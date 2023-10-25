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
import codesquad.gaemimarble.game.dto.request.GameEventRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GameReadyRequest;
import codesquad.gaemimarble.game.dto.request.GameRollDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameStartRequest;
import codesquad.gaemimarble.game.dto.request.GameStockBuyRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import codesquad.gaemimarble.game.dto.response.GameEventListResponse;
import codesquad.gaemimarble.game.dto.response.GameReadyResponse;
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
		typeMap.put("buy", GameStockBuyRequest.class);
		typeMap.put(TypeConstants.START, GameStartRequest.class);
		typeMap.put(TypeConstants.DICE, GameRollDiceRequest.class);
		typeMap.put(TypeConstants.EVENTS, GameEventRequest.class);
		typeMap.put(TypeConstants.EVENTS_RESULT, GameEventResultRequest.class);

		this.handlers = new HashMap<>();
		handlers.put(GameReadyRequest.class, req -> sendReadyStatus((GameReadyRequest)req));
		handlers.put(GameStartRequest.class, req -> sendFirstPlayer((GameStartRequest)req));
		handlers.put(GameRollDiceRequest.class, req -> sendDiceResult((GameRollDiceRequest)req));
		handlers.put(GameEventRequest.class, req -> sendRandomEvents((GameEventRequest)req));
		handlers.put(GameEventResultRequest.class, req -> sendEventResult((GameEventResultRequest)req));
		handlers.put(GameStockBuyRequest.class, req -> handleRequest((GameStockBuyRequest)req));
	}

	private void sendEventResult(GameEventResultRequest gameEventResultRequest) {
		socketDataSender.send(gameEventResultRequest.getGameId(), new ResponseDTO<>(TypeConstants.EVENTS_RESULT,
			gameService.proceedEvent(gameEventResultRequest)));
	}

	@PostMapping("/api/games")
	public ResponseEntity<GameRoomCreateResponse> createRoom() {
		GameRoomCreateResponse gameRoomCreateResponse = gameService.createRoom();
		socketDataSender.createRoom(gameRoomCreateResponse.getGameId());
		return ResponseEntity.status(HttpStatus.CREATED).body(gameRoomCreateResponse);
	}

	public void enterGame(Long gameId, WebSocketSession session, String playerId) {
		socketDataSender.saveSocket(gameId, session);
		socketDataSender.send(gameId, new ResponseDTO<>(TypeConstants.ENTER,gameService.enterGame(gameId, playerId)));
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
			GameReadyResponse.builder()
				.isReady(gameReadyRequest.getIsReady())
				.playerId(gameReadyRequest.getPlayerId()).build()));
	}

	private void sendFirstPlayer(GameStartRequest gameStartRequest) {
		String playerId = gameService.getFirstPlayer(gameStartRequest.getGameId());
		socketDataSender.send(gameStartRequest.getGameId(), new ResponseDTO<>(TypeConstants.START,
			Map.of("playerId", playerId)));
	}

	private void sendDiceResult(GameRollDiceRequest gameRollDiceRequest) {
		socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.DICE,
			gameService.rollDice(gameRollDiceRequest)));
		socketDataSender.send(gameRollDiceRequest.getGameId(), new ResponseDTO<>(TypeConstants.CELL,
			gameService.arriveAtCell(gameRollDiceRequest)));
	}

	private void sendRandomEvents(GameEventRequest gameEventRequest) {
		GameEventListResponse gameEventListResponse = gameService.selectEvents(gameEventRequest);
	}

	private void handleRequest(GameStockBuyRequest gameStockBuyRequest) {
		System.out.println(2);
	}
}
