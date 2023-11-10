package codesquad.gaemimarble.game.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import codesquad.gaemimarble.exception.PlayTimeException;
import codesquad.gaemimarble.game.dto.ResponseDTO;
import codesquad.gaemimarble.game.dto.request.GameBailRequest;
import codesquad.gaemimarble.game.dto.request.GameCellArrivalRequest;
import codesquad.gaemimarble.game.dto.request.GameEndTurnRequest;
import codesquad.gaemimarble.game.dto.request.GameEventRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GamePrisonDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameReadyRequest;
import codesquad.gaemimarble.game.dto.request.GameRobRequest;
import codesquad.gaemimarble.game.dto.request.GameRollDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameSellStockRequest;
import codesquad.gaemimarble.game.dto.request.GameStartRequest;
import codesquad.gaemimarble.game.dto.request.GameStatusBoardRequest;
import codesquad.gaemimarble.game.dto.request.GameStockBuyRequest;
import codesquad.gaemimarble.game.dto.request.GameTeleportRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameDiceResult;
import codesquad.gaemimarble.game.dto.response.GameEventListResponse;
import codesquad.gaemimarble.game.dto.response.GameEventNameResponse;
import codesquad.gaemimarble.game.dto.response.GameEventResponse;
import codesquad.gaemimarble.game.dto.response.GameRoomCreateResponse;
import codesquad.gaemimarble.game.dto.response.GameTeleportResponse;
import codesquad.gaemimarble.game.dto.response.userStatusBoard.GameUserBoardResponse;
import codesquad.gaemimarble.game.entity.Player;
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
		typeMap.put(TypeConstants.TELEPORT, GameTeleportRequest.class);
		typeMap.put(TypeConstants.ROB, GameRobRequest.class);
		typeMap.put(TypeConstants.STATUS_BOARD, GameStatusBoardRequest.class);
		typeMap.put(TypeConstants.CELL, GameCellArrivalRequest.class);

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
		handlers.put(GameTeleportRequest.class, req -> sendTeleport((GameTeleportRequest)req));
		handlers.put(GameRobRequest.class, req -> sendRobResult((GameRobRequest)req));
		handlers.put(GameStatusBoardRequest.class, req -> sendStatusBoard((GameStatusBoardRequest)req));
		handlers.put(GameCellArrivalRequest.class, req -> sendCellArrival((GameCellArrivalRequest)req));
	}

	private void sendRobResult(GameRobRequest gameRobRequest) {
		List<Player> players = gameService.rob(gameRobRequest);
		players.forEach(
			p -> socketDataSender.send(gameRobRequest.getGameId(), new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
				gameService.createUserBoardResponse(p))));
	}

	private void sendNextPlayer(GameEndTurnRequest gameEndTurnRequest) {
		socketDataSender.send(gameEndTurnRequest.getGameId(), new ResponseDTO<>(TypeConstants.END_TURN,
			gameService.endTurn(gameEndTurnRequest)));
	}

	private void sendSellResult(GameSellStockRequest gameSellStockRequest) {
		socketDataSender.send(gameSellStockRequest.getGameId(), new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
			gameService.sellStock(gameSellStockRequest)));
	}

	private void sendEventResult(GameEventResultRequest gameEventResultRequest) {
		GameEventNameResponse gameEventNameResponse = gameService.selectEvent(gameEventResultRequest);
		socketDataSender.send(gameEventResultRequest.getGameId(), new ResponseDTO<>(TypeConstants.EVENTS_RESULT,
			gameEventNameResponse));
		gameService.proceedEvent(gameEventNameResponse.getName(), gameEventResultRequest.getGameId());
		if (gameService.checkGameOver(gameEventResultRequest.getGameId())) {
			socketDataSender.send(
				gameEventResultRequest.getGameId(), new ResponseDTO<>(TypeConstants.GAME_OVER,
					gameService.createUserRanking(gameEventResultRequest.getGameId())));
		}
	}

	@PostMapping("/api/games")
	public ResponseEntity<GameRoomCreateResponse> createRoom() {
		GameRoomCreateResponse gameRoomCreateResponse = gameService.createRoom();
		socketDataSender.createRoom(gameRoomCreateResponse.getGameId());
		return ResponseEntity.status(HttpStatus.CREATED).body(gameRoomCreateResponse);
	}

	public void enterGame(Long gameId, WebSocketSession session, String playerId) {
		if (socketDataSender.saveSocket(gameId, playerId, session)) {
			if (gameService.checkSellingTime(gameId)) {
				socketDataSender.sendToPlayer(playerId,gameId, new ResponseDTO<>(TypeConstants.EVENTS,
					gameService.selectedEvents(gameId)));
			}
			socketDataSender.sendToPlayer(playerId,gameId, new ResponseDTO<>(TypeConstants.CURRENT_PLAYER,
				gameService.getCurrentPlayer(gameId)));
			socketDataSender.sendToPlayer(playerId,gameId, new ResponseDTO<>(TypeConstants.LOCATIONS,
				gameService.getLocations(gameId)));
			socketDataSender.sendToPlayer(playerId,gameId,
				new ResponseDTO<>(TypeConstants.ENTER, gameService.reenter(gameId)));
			sendAllUserStatusBoardResponseToPlayer(playerId,gameId);
			socketDataSender.sendToPlayer(playerId,gameId, new ResponseDTO<>(TypeConstants.STATUS_BOARD,
				gameService.createGameStatusBoardResponse(gameId)));
		} else {
			socketDataSender.send(gameId,
				new ResponseDTO<>(TypeConstants.ENTER, gameService.enterGame(gameId, playerId)));
		}
	}

	private void sendStatusBoard(GameStatusBoardRequest gameStatusBoardRequest) {
		socketDataSender.send(gameStatusBoardRequest.getGameId(), new ResponseDTO<>(TypeConstants.STATUS_BOARD,
			gameService.createGameStatusBoardResponse(gameStatusBoardRequest.getGameId())));
		sendAllUserStatusBoardResponse(gameStatusBoardRequest.getGameId());
	}

	private void sendAllUserStatusBoardResponseToPlayer(String playerId, Long gameId) {
		List<GameUserBoardResponse> gameUserBoardResponses = gameService.createUserStatusBoardResponse(
			gameId);
		for (GameUserBoardResponse gameUserBoardResponse : gameUserBoardResponses) {
			socketDataSender.sendToPlayer(playerId,gameId, new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
				gameUserBoardResponse));
		}
	}
	private void sendAllUserStatusBoardResponse(Long gameId) {
		List<GameUserBoardResponse> gameUserBoardResponses = gameService.createUserStatusBoardResponse(
			gameId);
		for (GameUserBoardResponse gameUserBoardResponse : gameUserBoardResponses) {
			socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
				gameUserBoardResponse));
		}
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

	// 게임 시작
	private void sendFirstPlayer(GameStartRequest gameStartRequest) {
		String playerId = gameService.getFirstPlayer(gameStartRequest.getGameId());
		socketDataSender.send(gameStartRequest.getGameId(), new ResponseDTO<>(TypeConstants.START,
			Map.of("playerId", playerId)));
		socketDataSender.send(gameStartRequest.getGameId(), new ResponseDTO<>(TypeConstants.STATUS_BOARD,
			gameService.createGameStatusBoardResponse(gameStartRequest.getGameId())));
		List<GameUserBoardResponse> gameUserBoardResponses = gameService.createUserStatusBoardResponse(
			gameStartRequest.getGameId());
		for (GameUserBoardResponse gameUserBoardResponse : gameUserBoardResponses) {
			socketDataSender.send(gameStartRequest.getGameId(), new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
				gameUserBoardResponse));
		}
	}

	private void sendDiceResult(GameRollDiceRequest gameRollDiceRequest) {
		GameDiceResult gameDiceResult = gameService.rollDice(gameRollDiceRequest.getGameId(),
			gameRollDiceRequest.getPlayerId());
		socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.DICE,
			gameDiceResult));
		if (gameDiceResult.getTripleDouble()) {
			socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.TELEPORT,
				GameTeleportResponse.builder().location(6).build()));
		}
	}

	private void sendCellArrival(GameCellArrivalRequest gameCellArrivalRequest) {
		GameCellResponse gameCellResponse = gameService.arriveAtCell(gameCellArrivalRequest.getGameId(), gameCellArrivalRequest.getPlayerId());
		socketDataSender.send(gameCellArrivalRequest.getGameId(), new ResponseDTO<>(TypeConstants.CELL,
			gameCellResponse));
		actCell(gameCellArrivalRequest.getGameId(), gameCellResponse);
	}

	private void sendTeleport(GameTeleportRequest gameTeleportRequest) {
		gameService.teleport(gameTeleportRequest);
		socketDataSender.send(gameTeleportRequest.getGameId(), new ResponseDTO<>(TypeConstants.TELEPORT,
			GameTeleportResponse.builder().location(gameTeleportRequest.getLocation()).build()));
	}

	private void sendRandomEvents(GameEventRequest gameEventRequest) {
		GameEventListResponse eventListResponse = gameService.selectEvents(gameEventRequest.getGameId());
		socketDataSender.send(gameEventRequest.getGameId(), new ResponseDTO<>(TypeConstants.EVENTS,
			eventListResponse));
		gameService.waitToSell(gameEventRequest.getGameId());
		sendEventResult(GameEventResultRequest.builder().gameId(gameEventRequest.getGameId())
			.events(eventListResponse.getEvents().stream().map(GameEventResponse::getTitle).toList()).build());
	}

	private void sendBuyResult(GameStockBuyRequest gameStockBuyRequest) {
		socketDataSender.send(gameStockBuyRequest.getGameId(), new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
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

	private void sendUserStatusBoardResponse(Long gameId, String playerId){
		socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
			gameService.createUserStatusBoardResponse(gameId)
				.stream()
				.filter(o -> o.getPlayerId().equals(playerId))
				.findFirst().orElseThrow(()-> new PlayTimeException("잘못된 플레이어 아이디입니다", playerId, gameId))));
	}

	private void actCell(Long gameId, GameCellResponse gameCellResponse) {
		switch (gameCellResponse.getLocation()) {
			case 0, 6, 18: // 시작, 감옥, 순간이동
				sendUserStatusBoardResponse(gameId, gameCellResponse.getPlayerId());
				break;
			case 9, 21: // 황금카드
				socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.GOLD_CARD,
					gameService.selectGoldCard(gameId, gameCellResponse.getPlayerId())));
				sendUserStatusBoardResponse(gameId, gameCellResponse.getPlayerId());
				break;
			case 12: // 호재
				socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.STATUS_BOARD,
					gameService.increasePlayerStockPrice(gameId, gameCellResponse.getPlayerId())));
				sendAllUserStatusBoardResponse(gameId);
				break;
			case 15: // 세금
				socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.USER_STATUS_BOARD,
					gameService.payExpense(gameId, gameCellResponse.getPlayerId(), 10_000_000)));
				break;
			default: // 기업
				socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.STATUS_BOARD,
					gameService.increaseCompanyStock(gameId, gameCellResponse.getLocation())));
				sendAllUserStatusBoardResponse(gameId);
				break;
		}
	}
}
