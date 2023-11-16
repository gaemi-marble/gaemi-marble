package codesquad.gaemimarble.game.player.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.gaemimarble.game.dto.GameMapper;
import codesquad.gaemimarble.game.dto.request.GameReadyRequest;
import codesquad.gaemimarble.game.dto.request.GoldCardRequest.GameDonationRequest;
import codesquad.gaemimarble.game.dto.request.GoldCardRequest.GameRobRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameCellResponse;
import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import codesquad.gaemimarble.game.dto.response.GameLocationResponse;
import codesquad.gaemimarble.game.dto.response.GameReadyResponse;
import codesquad.gaemimarble.game.dto.response.PlayerAsset;
import codesquad.gaemimarble.game.dto.response.UserRankingResponse;
import codesquad.gaemimarble.game.dto.response.userStatusBoard.GameUserBoardResponse;
import codesquad.gaemimarble.game.entity.Player;
import codesquad.gaemimarble.game.entity.Stock;
import codesquad.gaemimarble.game.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
	private final PlayerRepository playerRepository;

	public List<GameEnterResponse> enterGame(Long gameId, String playerId) {
		Player player = Player.init(playerId);
		List<Player> players = playerRepository.enterGame(gameId, player);
		return players.stream()
			.map(p -> GameEnterResponse.of(p.getOrder(), p.getPlayerId(), p.getIsReady()))
			.collect(Collectors.toList());
	}

	public String getFirstPlayer(Long gameId) {
		List<Player> players = playerRepository.getAllPlayer(gameId);
		int randomIndex = (int)(Math.random() * players.size()) + 1;
		Player player = players.get(randomIndex - 1);

		setOrder(player.getOrder(), players);
		gameStatus.initCurrentPlayerInfo(player);
		return player.getPlayerId();
	}

	public GameReadyResponse readyGame(GameReadyRequest gameReadyRequest) {
		Player player = playerRepository.getPlayer(gameReadyRequest.getGameId(), gameReadyRequest.getPlayerId());
		player.setReady(gameReadyRequest.getIsReady());
		return GameReadyResponse.builder()
			.playerId(player.getPlayerId())
			.isReady(player.getIsReady())
			.build();
	}

	public GameAccessibleResponse checkAccessibility(Long gameId) {
		List<Player> players = playerRepository.getAllPlayer(gameId);
		boolean isPresent = false;
		boolean isFull = false;
		if (players != null) {
			isPresent = true;
			if (players.size() == 4) {
				isFull = true;
			}
		}
		return GameAccessibleResponse.builder().isPresent(isPresent).isFull(isFull).build();
	}

	public GameCellResponse arriveAtCell(Long gameId, String playerId) {
		Player player = playerRepository.getPlayer(gameId, playerId);

		int location = player.getLocation();
		int salary = 0;
		int dividend = 0;
		if (location > 23) {
			salary = 5_000_000;
			dividend = (player.getStockAsset() * 5) / 100;
			dividend = (dividend / 100_000) * 100_000;

			player.setLocation(location % 24);
		}
		player.addCashAsset(salary + dividend);

		return GameCellResponse.builder()
			.playerId(player.getPlayerId())
			.location(player.getLocation())
			.build();
	}

	public GameUserBoardResponse payExpense(Long gameId, String playerId, int expense) {
		Player player = playerRepository.getPlayer(gameId, playerId);
		player.addCashAsset(-expense);
		return createUserBoardResponse(player);
	}

	public void updatePlayersAsset(List<Player> players, List<Stock> stockList) {
		for (Player player : players) {
			for (Stock stock : stockList) {
				if (player.getMyStocks().containsKey(stock.getName())) {
					player.updateStockAsset(stock);
				}
			}
		}
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

	public List<GameUserBoardResponse> createUserStatusBoardResponse(Long gameId) {
		return playerRepository.getAllPlayer(gameId)
			.stream()
			.map(this::createUserBoardResponse)
			.toList();
	}

	public List<GameLocationResponse> getLocations(Long gameId) {
		return playerRepository.getAllPlayer(gameId)
			.stream()
			.map(p -> GameLocationResponse.builder()
				.playerId(p.getPlayerId())
				.location(p.getLocation())
				.build())
			.toList();
	}

	public UserRankingResponse createUserRanking(Long gameId) {
		return UserRankingResponse.builder().ranking(playerRepository.getAllPlayer(gameId)
			.stream()
			.sorted(Comparator.comparing(Player::getTotalAsset).reversed())
			.map(p -> PlayerAsset.builder().playerId(p.getPlayerId()).totalAsset(p.getTotalAsset()).build())
			.collect(Collectors.toList())).build();
	}

	public List<Player> rob(GameRobRequest gameRobRequest) {
		Player taker = playerRepository.getPlayer(gameRobRequest.getGameId(), gameRobRequest.getPlayerId());
		taker.addCashAsset(10_000_000);
		Player target = playerRepository.getPlayer(gameRobRequest.getGameId(), gameRobRequest.getTargetId());
		target.addCashAsset(-10_000_000);
		return List.of(taker, target);
	}

	public List<Player> donate(GameDonationRequest gameDonationRequest) {
		Player giver = playerRepository.getPlayer(gameDonationRequest.getGameId(), gameDonationRequest.getPlayerId());
		giver.addCashAsset(-10_000_000);
		Player targetId = playerRepository.getPlayer(gameDonationRequest.getGameId(),
			gameDonationRequest.getTargetId());
		targetId.addCashAsset(10_000_000);
		return List.of(giver, targetId);
	}

	private void setOrder(Integer firstOrder, List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setOrder(((i + players.size() - firstOrder + 1) % players.size()) + 1);
			log.info("설정된 오더 (아이디 + 오더)" + players.get(i).getPlayerId() + "/" + players.get(i).getOrder());
		}
	}
}
