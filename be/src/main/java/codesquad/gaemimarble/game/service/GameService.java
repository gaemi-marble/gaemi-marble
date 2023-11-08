package codesquad.gaemimarble.game.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.gaemimarble.exception.PlayTimeException;
import codesquad.gaemimarble.game.dto.GameMapper;
import codesquad.gaemimarble.game.dto.request.GameEndTurnRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GamePrisonDiceRequest;
import codesquad.gaemimarble.game.dto.request.GameReadyRequest;
import codesquad.gaemimarble.game.dto.request.GameRobRequest;
import codesquad.gaemimarble.game.dto.request.GameSellStockRequest;
import codesquad.gaemimarble.game.dto.request.GameStockBuyRequest;
import codesquad.gaemimarble.game.dto.request.GameTeleportRequest;
import codesquad.gaemimarble.game.dto.request.StockNameQuantity;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameCurrentPlayerIdResponse;
import codesquad.gaemimarble.game.dto.response.GameDiceResult;
import codesquad.gaemimarble.game.dto.response.GameEndTurnResponse;
import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import codesquad.gaemimarble.game.dto.response.GameEventListResponse;
import codesquad.gaemimarble.game.dto.response.GameEventNameResponse;
import codesquad.gaemimarble.game.dto.response.GameEventResponse;
import codesquad.gaemimarble.game.dto.response.GameExpenseResponse;
import codesquad.gaemimarble.game.dto.response.GameGoldCardResponse;
import codesquad.gaemimarble.game.dto.response.GameLocationResponse;
import codesquad.gaemimarble.game.dto.response.GamePrisonDiceResponse;
import codesquad.gaemimarble.game.dto.response.GameReadyResponse;
import codesquad.gaemimarble.game.dto.response.GameRoomCreateResponse;
import codesquad.gaemimarble.game.dto.response.PlayerAsset;
import codesquad.gaemimarble.game.dto.response.UserRankingResponse;
import codesquad.gaemimarble.game.dto.response.generalStatusBoard.GameStatusBoardResponse;
import codesquad.gaemimarble.game.dto.response.userStatusBoard.GameUserBoardResponse;
import codesquad.gaemimarble.game.entity.Board;
import codesquad.gaemimarble.game.entity.CurrentPlayerInfo;
import codesquad.gaemimarble.game.entity.Events;
import codesquad.gaemimarble.game.entity.GameStatus;
import codesquad.gaemimarble.game.entity.GoldCard;
import codesquad.gaemimarble.game.entity.Player;
import codesquad.gaemimarble.game.entity.Stock;
import codesquad.gaemimarble.game.entity.Theme;
import codesquad.gaemimarble.game.repository.GameRepository;
import codesquad.gaemimarble.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
	private final GameRepository gameRepository;

	public GameRoomCreateResponse createRoom() {
		GameStatus gameStatus = GameStatus.builder()
			.players(new ArrayList<>())
			.stocks(Stock.initStocks())
			.roundCount(0)
			.isStarted(false)
			.board(new Board())
			.selectedEvents(new ArrayList<>())
			.sellingTime(Constants.SELLING_TIME)
			.build();
		Long gameRoomId = gameRepository.createRoom(gameStatus);
		return GameRoomCreateResponse.builder().gameId(gameRoomId).build();
	}

	public List<GameEnterResponse> enterGame(Long gameId, String playerId) {
		Player player = Player.init(playerId);
		List<Player> players = gameRepository.enterGame(gameId, player);
		return players.stream()
			.map(p -> GameEnterResponse.of(p.getOrder(), p.getPlayerId(), p.getIsReady()))
			.collect(Collectors.toList());
	}

	public GameReadyResponse readyGame(GameReadyRequest gameReadyRequest) {
		Player player = gameRepository.getGameStatus(gameReadyRequest.getGameId())
			.getPlayer(gameReadyRequest.getPlayerId());
		player.setReady(gameReadyRequest.getIsReady());
		return GameReadyResponse.builder()
			.playerId(player.getPlayerId())
			.isReady(player.getIsReady())
			.build();
	}

	public String getFirstPlayer(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		List<Player> players = gameStatus.getPlayers();
		int randomIndex = (int)(Math.random() * players.size()) + 1;
		Player player = players.get(randomIndex - 1);

		log.info("first order" + player.getOrder());
		gameStatus.setOrder(player.getOrder());
		gameStatus.initCurrentPlayerInfo(player);
		return player.getPlayerId();
	}

	public GameAccessibleResponse checkAccessibility(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		boolean isPresent = false;
		boolean isFull = false;
		if (gameStatus != null) {
			isPresent = true;
			if (gameStatus.getPlayers().size() == 4) {
				isFull = true;
			}
		}

		return GameAccessibleResponse.builder().isPresent(isPresent).isFull(isFull).build();
	}

	public GameDiceResult rollDice(Long gameId, String playerId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		Player player = gameStatus.getPlayer(playerId);
		int startLocation = player.getLocation();

		int dice1 = (int)(Math.random() * 6) + 1;
		int dice2 = (int)(Math.random() * 6) + 1;

		if (dice1 == dice2) {
			int countDouble = gameStatus.getCurrentPlayerInfo().increaseCountDouble();

			if (countDouble == 3) {
				player.goToPrison();
				return new GameDiceResult(true, dice1, dice2);
			}
		}
		player.move(dice1 + dice2);
		return new GameDiceResult(false, dice1, dice2);
	}

	public GameCellResponse arriveAtCell(Long gameId, String playerId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		Player player = gameStatus.getPlayer(playerId);

		int location = player.getLocation();
		int salary = 0;
		if (location > 23) {
			salary = 5_000_000;
			player.setLocation(location % 24);
		}
		int dividend = (int)((player.getStockAsset() * 5) / 100);
		dividend = (dividend / 100_000) * 100_000;
		player.addCashAsset(salary + dividend);

		return GameCellResponse.builder()
			.playerId(player.getPlayerId())
			.location(player.getLocation())
			.salary(salary)
			.dividend(dividend)
			.build();
	}

	public GameExpenseResponse payExpense(Long gameId, String playerId, int expense) {
		Player player = gameRepository.getGameStatus(gameId).getPlayer(playerId);
		player.addCashAsset(-expense);
		return GameExpenseResponse.builder()
			.playerId(player.getPlayerId())
			.amount(expense)
			.build();
	}

	public GameEventListResponse selectEvents(Long gameId) {
		List<Integer> numbers = new ArrayList<>();
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		GameEventListResponse gameEventListResponse = GameEventListResponse.builder().
			timer(gameStatus.getSellingTime()).events(new ArrayList<>()).build();

		for (int i = 1; i <= Events.values().length; i++) {
			numbers.add(i);
		}

		List<Integer> selectedNumbers = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			int randomIndex = random.nextInt(numbers.size());
			selectedNumbers.add(numbers.get(randomIndex));
			numbers.remove(randomIndex);
		}
		int count = 1;
		for (Events event : Events.values()) {
			if (selectedNumbers.contains(count++)) {
				gameEventListResponse.getEvents()
					.add(GameEventResponse.builder()
						.title(event.getTitle())
						.content(event.getContents())
						.impact(event.getImpactDescription())
						.build());
				gameStatus.getSelectedEvents().add(event);
			}
		}
		return gameEventListResponse;
	}

	public GameEventNameResponse selectEvent(GameEventResultRequest gameEventResultRequest) {
		int randomIndex = (int)(Math.random() * 6);
		return GameEventNameResponse.builder().name(gameEventResultRequest.getEvents().get(randomIndex)).build();
	}

	public GameStatusBoardResponse proceedEvent(String eventName, Long gameId) {
		Events eventToProceed = null;
		for (Events events : Events.values()) {
			if (events.getTitle().equals(eventName)) {
				eventToProceed = events;
			}
		}
		if (eventToProceed == null) {
			throw new PlayTimeException("이벤트 이름이 맞지 않습니다", null, gameId);
		}
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		Map<Theme, Integer> impactMap = eventToProceed.getImpact();
		List<Stock> stockList = gameStatus.getStocks();
		for (Stock stock : stockList) {
			if (impactMap.containsKey(stock.getTheme())) {
				stock.changePrice(impactMap.get(stock.getTheme()));
			} else {
				Random random = new Random();
				stock.changePrice(random.nextBoolean() ? -10 : 10);
			}
		}
		updatePlayersAsset(gameStatus.getPlayers(), stockList);
		gameStatus.incrementRoundCount();
		gameStatus.getSelectedEvents().clear();

		return createGameStatusBoardResponse(gameId);
	}

	private void updatePlayersAsset(List<Player> players, List<Stock> stockList) {
		for (Player player : players) {
			for (Stock stock : stockList) {
				if (player.getMyStocks().containsKey(stock.getName())) {
					player.updateStockAsset(stock);
				}
			}
		}
	}

	public GameStatusBoardResponse createGameStatusBoardResponse(Long gameId) {
		List<Stock> stockList = gameRepository.getGameStatus(gameId).getStocks();
		return GameStatusBoardResponse.builder()
			.stockStatusBoard(stockList.stream()
				.map(stock -> GameMapper.INSTANCE.toGameStockStatusResponse(stock, stock.getTheme().getName()))
				.collect(Collectors.toList()))
			.build();
	}

	public GameUserBoardResponse buyStock(GameStockBuyRequest gameStockBuyRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameStockBuyRequest.getGameId());
		Player player = gameRepository.getPlayer(gameStockBuyRequest.getGameId(), gameStockBuyRequest.getPlayerId());
		Stock stock = gameStatus.getStocks()
			.stream()
			.filter(s -> s.getName().equals(gameStockBuyRequest.getStockName()))
			.findFirst()
			.orElseThrow(() -> new PlayTimeException("존재하지 않는 주식이름입니다", gameStockBuyRequest.getPlayerId(),
				gameStockBuyRequest.getGameId()));
		if (stock.getRemainingStock() < gameStockBuyRequest.getQuantity()
			| player.getCashAsset() < stock.getCurrentPrice() * gameStockBuyRequest.getQuantity()) {
			throw new PlayTimeException("구매할 수량이 부족하거나, 플레이어 보유 캐쉬가 부족합니다", gameStockBuyRequest.getPlayerId(),
				gameStockBuyRequest.getGameId());
		}
		player.buy(stock, gameStockBuyRequest.getQuantity());
		stock.decrementQuantity(gameStockBuyRequest.getQuantity());
		return createUserBoardResponse(player);
	}

	public GameUserBoardResponse createUserBoardResponse(Player player) {
		for (String key : player.getMyStocks().keySet()) {
			if (player.getMyStocks().get(key) == 0) {
				player.getMyStocks().remove(key);
			}
		}
		return GameUserBoardResponse.builder()
			.playerId(player.getPlayerId())
			.userStatusBoard(GameMapper.INSTANCE.toGameUserStatusBoardResponse(player, player.getMyStocks()
				.keySet()
				.stream()
				.map(k -> GameMapper.INSTANCE.toStockResponse(k, player.getMyStocks().get(k)))
				.toList()))
			.build();
	}

	public GameUserBoardResponse sellStock(GameSellStockRequest gameSellStockRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameSellStockRequest.getGameId());
		Player player = gameRepository.getPlayer(gameSellStockRequest.getGameId(), gameSellStockRequest.getPlayerId());

		Map<String, Integer> sellingStockInfoMap = new HashMap<>();
		for (StockNameQuantity stock : gameSellStockRequest.getStockList()) {
			sellingStockInfoMap.put(stock.getName(), stock.getQuantity());
		}
		for (String stockName : sellingStockInfoMap.keySet()) {
			if (player.getMyStocks().get(stockName) < sellingStockInfoMap.get(stockName)) {
				throw new PlayTimeException("플레이어가 보유한 주식보다 더 많이 팔수는 없습니다", gameSellStockRequest.getPlayerId(),
					gameSellStockRequest.getGameId());
			}
		}

		List<Stock> stocks = gameStatus.getStocks();
		for (Stock stock : stocks) {
			String stockName = stock.getName();
			if (sellingStockInfoMap.containsKey(stockName)) {
				stock.incrementQuantity(sellingStockInfoMap.get(stockName));
				player.sellStock(stock, sellingStockInfoMap.get(stockName));
			}
		}

		return createUserBoardResponse(player);

	}

	public GameEndTurnResponse endTurn(GameEndTurnRequest gameEndTurnRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameEndTurnRequest.getGameId());
		CurrentPlayerInfo currentPlayerInfo = gameStatus.getCurrentPlayerInfo();

		if (currentPlayerInfo.getRolledDouble()) {
			currentPlayerInfo.initRolledDouble();
			Integer location = gameStatus.getPlayer(currentPlayerInfo.getPlayerId()).getLocation();
			if (!(location == 18 || location == 6)) {
				return GameEndTurnResponse.builder().nextPlayerId(currentPlayerInfo.getPlayerId()).build();
			}
		}
		log.info("order: " + currentPlayerInfo.getOrder() + "game player size" + gameStatus.getPlayers().size());
		if (currentPlayerInfo.getOrder() != gameStatus.getPlayers().size()) {
			for (Player player : gameStatus.getPlayers()) {
				if (player.getOrder() == currentPlayerInfo.getOrder() + 1) {
					currentPlayerInfo.update(player);
					return GameEndTurnResponse.builder().nextPlayerId(player.getPlayerId()).build();
				}
			}
		}
		for (Player player : gameStatus.getPlayers()) {
			if (player.getOrder() == 1) {
				currentPlayerInfo.update(player);
			}
		}

		return GameEndTurnResponse.builder().nextPlayerId(null).build();
	}

	public void teleport(GameTeleportRequest gameTeleportRequest) {
		Player player = gameRepository.getPlayer(gameTeleportRequest.getGameId(), gameTeleportRequest.getPlayerId());
		if (gameTeleportRequest.getLocation().equals(player.getLocation()) && player.getLocation() == 18) {
			throw new PlayTimeException("순간이동 칸으로 이동 할 수 없습니다", gameTeleportRequest.getPlayerId(),
				gameTeleportRequest.getGameId());
		}
		player.setLocation(
			gameTeleportRequest.getLocation() > player.getLocation() ? gameTeleportRequest.getLocation() :
				gameTeleportRequest.getLocation() + 24);
	}

	public GameStatusBoardResponse increaseCompanyStock(Long gameId, Integer location) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		String shareName = gameStatus.getBoard().getBoard().get(location);
		Stock stock = gameStatus.getStocks().stream()
			.filter(s -> s.getName().equals(shareName)).findFirst()
			.orElseThrow(() -> new PlayTimeException("존재하지 않는 주식입니다.", null, gameId));
		if (stock.getWasBought()) {
			stock.changePrice(10);
		}
		return createGameStatusBoardResponse(gameId);
	}

	public GameStatusBoardResponse increasePlayerStockPrice(Long gameId, String playerId) {
		Player player = gameRepository.getPlayer(gameId, playerId);
		List<Stock> stockList = gameRepository.getGameStatus(gameId).getStocks()
			.stream()
			.filter(s -> player.getMyStocks().containsKey(s.getName()))
			.toList();
		for (Stock stock : stockList) {
			stock.changePrice(10);
		}
		return createGameStatusBoardResponse(gameId);

	}

	public GamePrisonDiceResponse prisonDice(GamePrisonDiceRequest gamePrisonDiceRequest) {
		GameStatus gameStatus = gameRepository.getGameStatus(gamePrisonDiceRequest.getGameId());
		Player player = gameStatus.getPlayer(gamePrisonDiceRequest.getPlayerId());

		int dice1 = (int)(Math.random() * 6) + 1;
		int dice2 = (int)(Math.random() * 6) + 1;

		if (dice1 == dice2) {
			player.escapePrison(dice1 + dice2);
		} else {
			player.increasePrisonCount();
			if (player.getPrisonCount() == 3) {
				player.escapePrison(dice1 + dice2);
			}
		}

		return GamePrisonDiceResponse.builder()
			.playerId(player.getPlayerId())
			.dice1(dice1)
			.dice2(dice2)
			.hasEscaped(player.getPrisonCount() == 0)
			.build();
	}

	public List<GameUserBoardResponse> createUserStatusBoardResponse(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		return gameStatus.getPlayers()
			.stream()
			.map(this::createUserBoardResponse)
			.toList();
	}

	public GameGoldCardResponse selectGoldCard(Long gameId, String playerId) {
		GoldCard goldCard = GoldCard.getRandomGoldCard();
		return GameGoldCardResponse.builder()
			.title(goldCard.getTitle())
			.description(goldCard.getDescription())
			.build();
	}

	public List<Player> rob(GameRobRequest gameRobRequest) {
		Player taker = gameRepository.getPlayer(gameRobRequest.getGameId(), gameRobRequest.getPlayerId());
		taker.addCashAsset(10_000_000);
		Player target = gameRepository.getPlayer(gameRobRequest.getGameId(), gameRobRequest.getTargetId());
		target.addCashAsset(-10_000_000);
		return List.of(taker, target);
	}

	public boolean checkGameOver(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		return gameStatus.getRoundCount() > 15;
	}

	public UserRankingResponse createUserRanking(Long gameId) {
		return UserRankingResponse.builder().ranking(gameRepository.getAllPlayer(gameId)
			.stream()
			.sorted(Comparator.comparing(Player::getTotalAsset).reversed())
			.map(p -> PlayerAsset.builder().playerId(p.getPlayerId()).totalAsset(p.getTotalAsset()).build())
			.collect(Collectors.toList())).build();
	}

	public GameCurrentPlayerIdResponse getCurrentPlayer(Long gameId) {
		return GameCurrentPlayerIdResponse.builder()
			.playerId(gameRepository.getGameStatus(gameId).getCurrentPlayerInfo().getPlayerId())
			.build();
	}

	public List<GameLocationResponse> getLocations(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		return gameStatus.getPlayers()
			.stream()
			.map(p -> GameLocationResponse.builder()
				.playerId(p.getPlayerId())
				.location(p.getLocation())
				.build())
			.toList();
	}

	public List<GameEnterResponse> reenter(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		AtomicInteger order = new AtomicInteger(1);
		return gameStatus.getPlayers()
			.stream()
			.map(p -> GameEnterResponse.of(order.getAndIncrement(), p.getPlayerId(), p.getIsReady()))
			.toList();
	}

	public void waitToSell(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		gameStatus.waitSellingTime();
	}

	public boolean checkSellingTime(Long gameId) {
		return gameRepository.getGameStatus(gameId).getSellingTime() < 5;
	}

	public GameEventListResponse selectedEvents(Long gameId) {
		GameStatus gameStatus = gameRepository.getGameStatus(gameId);
		GameEventListResponse gameEventListResponse = GameEventListResponse.builder().
			timer(gameStatus.getSellingTime()).events(new ArrayList<>()).build();
		for (Events event : gameStatus.getSelectedEvents()) {
			gameEventListResponse.getEvents()
				.add(GameEventResponse.builder()
					.title(event.getTitle())
					.content(event.getContents())
					.impact(event.getImpactDescription())
					.build());
		}
		return gameEventListResponse;
	}
}
