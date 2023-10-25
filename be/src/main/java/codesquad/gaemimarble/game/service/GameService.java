package codesquad.gaemimarble.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.gaemimarble.game.dto.GameMapper;
import codesquad.gaemimarble.game.dto.request.GameEventRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GameRollDiceRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameDiceResult;
import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import codesquad.gaemimarble.game.dto.response.GameEventListResponse;
import codesquad.gaemimarble.game.dto.response.GameEventResponse;
import codesquad.gaemimarble.game.dto.response.GameRoomCreateResponse;
import codesquad.gaemimarble.game.dto.response.GameStatusBoardResponse;
import codesquad.gaemimarble.game.entity.Board;
import codesquad.gaemimarble.game.entity.Events;
import codesquad.gaemimarble.game.entity.GameStatus;
import codesquad.gaemimarble.game.entity.Player;
import codesquad.gaemimarble.game.entity.Stock;
import codesquad.gaemimarble.game.entity.Theme;
import codesquad.gaemimarble.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {
	private final GameRepository gameRepository;

	public GameRoomCreateResponse createRoom() {
		GameStatus gameStatus = GameStatus.builder()
			.players(new ArrayList<>())
			.stocks(Stock.initStocks())
			.roundCount(0)
			.isStarted(false)
			.board(new Board())
			.build();
		Long gameRoomId = gameRepository.createRoom(gameStatus);
		return GameRoomCreateResponse.builder()
			.gameId(gameRoomId).build();
	}

	public List<GameEnterResponse> enterGame(Long gameId, String playerId) {
		Player player = Player.init(playerId);
		List<Player> players = gameRepository.enterGame(gameId, player);
		return players.stream()
			.map(p -> GameEnterResponse.of(p.getOrder(), p.getPlayerId()))
			.collect(Collectors.toList());
	}

	public String getFirstPlayer(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		List<Player> players = gameStatus.getPlayers();
		int randomIndex = (int)(Math.random() * players.size()) + 1;
		Player player = players.get(randomIndex-1);

		gameStatus.initCurrentPlayerInfo(player);
		gameStatus.setOrder(player.getOrder());
		return player.getPlayerId();
	}

	public GameAccessibleResponse checkAccessibility(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		boolean isPresent = true;
		boolean isFull = false;
		if (gameStatus != null) {
			isPresent = false;
		} else if (gameStatus.getPlayers().size() == 4) {
			isFull = true;
		}
		return GameAccessibleResponse.builder()
			.isPresent(isPresent)
			.isFull(isFull)
			.build();
	}

	public GameDiceResult rollDice(GameRollDiceRequest gameRollDiceRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameRollDiceRequest.getGameId());
		Player player = gameStatus.getPlayer(gameRollDiceRequest.getPlayerId());
		int startLocation = player.getLocation();

		if (startLocation == 6) {
			// 탈출 시도 or 보석금
			return null;
		}
		if (startLocation == 18) {
			// 순간 이동 진행
			return null;
		}

		int dice1 = (int)(Math.random() * 6) + 1;
		int dice2 = (int)(Math.random() * 6) + 1;

		if (dice1 == dice2) {
			int countDouble = gameStatus.increaseCountDouble();
			if (countDouble == 3) {
				player.goToPrison();
				return new GameDiceResult(startLocation, dice1, dice2);
			}
		}
		player.move(dice1 + dice2);
		return new GameDiceResult(startLocation, dice1, dice2);
	}

	public GameCellResponse arriveAtCell(GameRollDiceRequest gameRollDiceRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameRollDiceRequest.getGameId());
		Player player = gameStatus.getPlayer(gameRollDiceRequest.getPlayerId());

		int location = player.getLocation();
		int salary = 0;
		if (location > 23) {
			salary = 5_000_000;
			player.setLocation(location%24);
		}
		int dividend = (int)((player.getStockAsset() * 5) / 100);
		player.setAsset(salary, dividend);

		return GameCellResponse.builder()
			.playerId(player.getPlayerId())
			.location(player.getLocation())
			.salary(salary)
			.dividend(dividend)
			.build();
	}

	public GameEventListResponse selectEvents(GameEventRequest gameEventRequest) {
		List<Integer> numbers = new ArrayList<>();
		GameEventListResponse gameEventListResponse = GameEventListResponse.builder().events(new ArrayList<>()).build();

		for (int i = 1; i <= Events.values().length; i++) {
			numbers.add(i);
		}

		List<Integer> selectedNumbers = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			int randomIndex = random.nextInt(numbers.size());
			selectedNumbers.add(randomIndex);
			numbers.remove(randomIndex);
		}
		int count = 1;
		for (Events event : Events.values()) {
			if (selectedNumbers.contains(count)) {
				gameEventListResponse.getEvents().add(GameEventResponse.builder()
					.title(event.getTitle())
					.content(event.getContents())
					.impact(event.getImpactDescription())
					.build());
			}
		}
		return gameEventListResponse;

	}

	public GameStatusBoardResponse proceedEvent(GameEventResultRequest gameEventResultRequest) {
		Events eventToProceed = null;
		for (Events events : Events.values()) {
			if (events.getTitle().equals(gameEventResultRequest.getEventName())) {
				eventToProceed = events;
			}
		}
		if (eventToProceed == null) {
			throw new RuntimeException("이벤트 이름이 맞지 않습니다");
		}
		GameStatus gameStatus = gameRepository.getGameStatus(gameEventResultRequest.getGameId());
		Map<Theme, Integer> impactMap = eventToProceed.getImpact();
		List<Stock> stockList = gameStatus.getStocks();
		for (Stock stock : stockList) {
			if (impactMap.containsKey(stock.getTheme())) {
				stock.changePrice(impactMap.get(stock.getTheme()));
			}
		}
		return createGameStatusBoardResponse(gameEventResultRequest.getGameId());
	}

	public GameStatusBoardResponse createGameStatusBoardResponse(Long gameId) {
		List<Stock> stockList = gameRepository.getGameStatus(gameId).getStocks();
		return GameStatusBoardResponse.builder()
			.stockStatusBoard(stockList.stream()
				.map(stock -> GameMapper.INSTANCE.toGameStockStatusResponse(stock, stock.getTheme().getName()))
				.collect(Collectors.toList()))
			.build();
	}
}
