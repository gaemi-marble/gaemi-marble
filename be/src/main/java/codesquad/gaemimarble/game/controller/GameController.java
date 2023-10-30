package codesquad.gaemimarble.game.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import codesquad.gaemimarble.game.dto.ResponseDTO;
import codesquad.gaemimarble.game.dto.request.GameBailRequest;
import codesquad.gaemimarble.game.dto.request.GameEndTurnRequest;
import codesquad.gaemimarble.game.dto.request.GameEventRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GamePrisonDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameReadyRequest;
import codesquad.gaemimarble.game.dto.request.GameRollDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameSellStockRequest;
import codesquad.gaemimarble.game.dto.request.GameStartRequest;
import codesquad.gaemimarble.game.dto.request.GameStockBuyRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameEventNameResponse;
import codesquad.gaemimarble.game.dto.response.GameExpenseResponse;
import codesquad.gaemimarble.game.dto.response.GameRoomCreateResponse;
import codesquad.gaemimarble.game.entity.TypeConstants;
import codesquad.gaemimarble.game.service.GameService;

@RestController
public class GameController {
	private final Map<String, Class<?>> typeMap;
	private final Map<Class<?>, Consumer<Object>> handlers;
	private final GameService gameService;
	private final SocketDataSender socketDataSender;

	public GameController(GameService gameService, SocketDataSender socketDataSender) {
		this.gameService = gameService;
		this.socketDataSender = socketDataSender;
		this.typeMap = new HashMap<>();
		typeMap.put(TypeConstants.READY, GameReadyRequest.class);
		typeMap.put(TypeConstants.START, GameStartRequest.class);
		typeMap.put(TypeConstants.DICE, GameRollDiceRequest.class);
		typeMap.put(TypeConstants.EVENTS, GameEventRequest.class);
		typeMap.put(TypeConstants.EVENTS_RESULT, GameEventResultRequest.class);
		typeMap.put(TypeConstants.BUY, GameStockBuyRequest.class);
		typeMap.put(TypeConstants.SELL, GameSellStockRequest.class);
		typeMap.put(TypeConstants.END_TURN, GameEndTurnRequest.class);
		typeMap.put(TypeConstants.PRISON_DICE, GamePrisonDiceRequest.class);
		typeMap.put(TypeConstants.BAIL, GameBailRequest.class);

		this.handlers = new HashMap<>();
		handlers.put(GameReadyRequest.class, req -> sendReadyStatus((GameReadyRequest)req));
		handlers.put(GameStartRequest.class, req -> sendFirstPlayer((GameStartRequest)req));
		handlers.put(GameRollDiceRequest.class, req -> sendDiceResult((GameRollDiceRequest)req));
		handlers.put(GameEventRequest.class, req -> sendRandomEvents((GameEventRequest)req));
		handlers.put(GameEventResultRequest.class, req -> sendEventResult((GameEventResultRequest)req));
		handlers.put(GameStockBuyRequest.class, req -> sendBuyResult((GameStockBuyRequest)req));
		handlers.put(GameSellStockRequest.class, req -> sendSellResult((GameSellStockRequest)req));
		handlers.put(GameEndTurnRequest.class, req -> sendNextPlayer((GameEndTurnRequest)req));
		handlers.put(GamePrisonDiceRequest.class, req -> sendPrisonDiceResult((GamePrisonDiceRequest)req));
		handlers.put(GameBailRequest.class, req -> sendBailResult((GameBailRequest)req));
	}

	private void sendNextPlayer(GameEndTurnRequest gameEndTurnRequest) {
		socketDataSender.send(gameEndTurnRequest.getGameId(), new ResponseDTO<>(TypeConstants.END_TURN,
			gameService.endTurn(gameEndTurnRequest)));
	}

	private void sendSellResult(GameSellStockRequest gameSellStockRequest) {
		socketDataSender.send(gameSellStockRequest.getGameId(), new ResponseDTO<>(TypeConstants.SELL,
			gameService.sellStock(gameSellStockRequest)));
	}

	private void sendEventResult(GameEventResultRequest gameEventResultRequest) {
		GameEventNameResponse gameEventNameResponse = gameService.selectEvent(gameEventResultRequest);
		socketDataSender.send(gameEventResultRequest.getGameId(), new ResponseDTO<>(TypeConstants.EVENTS_RESULT,
			gameEventNameResponse));
		socketDataSender.send(gameEventResultRequest.getGameId(), new ResponseDTO<>(TypeConstants.STATUS_BOARD,
			gameService.proceedEvent(gameEventNameResponse.getName(), gameEventResultRequest.getGameId())));
	}

	@PostMapping("/api/games")
	public ResponseEntity<GameRoomCreateResponse> createRoom() {
		GameRoomCreateResponse gameRoomCreateResponse = gameService.createRoom();
		socketDataSender.createRoom(gameRoomCreateResponse.getGameId());
		return ResponseEntity.status(HttpStatus.CREATED).body(gameRoomCreateResponse);
	}

	public void enterGame(Long gameId, WebSocketSession session, String playerId) {
		socketDataSender.saveSocket(gameId, session);
		socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.ENTER, gameService.enterGame(gameId, playerId)));
	}

	@GetMapping("/api/games/{gameId}")
	public ResponseEntity<GameAccessibleResponse> checkAccessiblity(@PathVariable Long gameId) {
		return ResponseEntity.ok().body(gameService.checkAccessibility(gameId));
	}

	public Map<String, Class<?>> getTypeMap() {
		return typeMap;
	}

	public void handleRequest(Object request) {
		Consumer<Object> handler = handlers.get(request.getClass());
		if (handler != null) {
			handler.accept(request);
		} else {
			throw new IllegalArgumentException("Unknown request type");
		}
	}

	private void sendReadyStatus(GameReadyRequest gameReadyRequest) {
		socketDataSender.send(gameReadyRequest.getGameId(), new ResponseDTO<>(TypeConstants.READY,
			gameService.readyGame(gameReadyRequest)));
	}

	private void sendFirstPlayer(GameStartRequest gameStartRequest) {
		String playerId = gameService.getFirstPlayer(gameStartRequest.getGameId());
		socketDataSender.send(gameStartRequest.getGameId(), new ResponseDTO<>(TypeConstants.START,
			Map.of("playerId", playerId)));
	}

	private void sendDiceResult(GameRollDiceRequest gameRollDiceRequest) {
		socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.DICE,
			gameService.rollDice(gameRollDiceRequest.getGameId(), gameRollDiceRequest.getPlayerId())));
		GameCellResponse gameCellResponse = gameService.arriveAtCell(gameRollDiceRequest);
		socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.CELL,
			gameCellResponse));
		actCell(gameRollDiceRequest.getGameId(), gameCellResponse);
	}

	private void sendRandomEvents(GameEventRequest gameEventRequest) {
		socketDataSender.send(gameEventRequest.getGameId(), new ResponseDTO<>(TypeConstants.EVENTS,
			gameService.selectEvents()));
	}

	private void sendBuyResult(GameStockBuyRequest gameStockBuyRequest) {
		socketDataSender.send(gameStockBuyRequest.getGameId(), new ResponseDTO<>(TypeConstants.BUY,
			gameService.buyStock(gameStockBuyRequest)));
	}

	public void sendPrisonDiceResult(GamePrisonDiceRequest gamePrisonDiceRequest) {
		socketDataSender.send(gamePrisonDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.PRISON_DICE,
			gameService.prisonDice(gamePrisonDiceRequest)));
	}

	public void sendBailResult(GameBailRequest gameBailRequest) {
		socketDataSender.send(gameBailRequest.getGameId(), new ResponseDTO<>(TypeConstants.EXPENSE,
			gameService.payExpense(gameBailRequest.getGameId(), gameBailRequest.getPlayerId(), 5_000_000)));
		socketDataSender.send(gameBailRequest.getGameId(), new ResponseDTO<>(TypeConstants.DICE,
			gameService.rollDice(gameBailRequest.getGameId(), gameBailRequest.getPlayerId())));
	}

	private void actCell(Long gameId, GameCellResponse gameCellResponse) {
		switch (gameCellResponse.getLocation()) {
			// case 9: // 황금카드
			// case 12: // 호재
			case 15: // 세금
				socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.EXPENSE,
					gameService.payExpense(gameId, gameCellResponse.getPlayerId(), 10_000_000)));
				break;
			//case 21: // 황금카드
			// default: // 기업
		}
	}
}
