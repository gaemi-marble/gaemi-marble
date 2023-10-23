package codesquad.gaemimarble.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import codesquad.gaemimarble.game.dto.request.GameEventRequest;
import codesquad.gaemimarble.game.dto.request.GameEventResultRequest;
import codesquad.gaemimarble.game.dto.request.GameRollDiceRequest;
import codesquad.gaemimarble.game.dto.response.GameAccessibleResponse;
import codesquad.gaemimarble.game.dto.response.GameDiceResult;
import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import codesquad.gaemimarble.game.dto.response.GameEventListResponse;
import codesquad.gaemimarble.game.dto.response.GameEventResponse;
import codesquad.gaemimarble.game.dto.response.GameRoomCreateResponse;
import codesquad.gaemimarble.game.entity.Board;
import codesquad.gaemimarble.game.entity.Events;
import codesquad.gaemimarble.game.entity.GameStatus;
import codesquad.gaemimarble.game.entity.Player;
import codesquad.gaemimarble.game.entity.Stock;
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
		List<Player> players = gameRepository.getAllPlayer(gameId);
		int randomIndex = (int)(Math.random() * players.size()) + 1;
		gameRepository.setOrder(gameId, players.get(randomIndex));
		return players.get(randomIndex).getPlayerId();
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
		// 여기서 이동 미리 반영?
		int dice1 = (int)(Math.random() * 6) + 1;
		int dice2 = (int)(Math.random() * 6) + 1;
		List<Player> players = gameRepository.getAllPlayer(gameRollDiceRequest.getGameId());
		Player currentPlayer = players.stream()
			.filter(player -> player.getPlayerId() == gameRollDiceRequest.getPlayerId())
			.findFirst().orElse(null);

		if (dice1 == dice2) {
			gameRepository.increaseCountDouble(currentPlayer);
		} else {
			gameRepository.updateCurrentPlayerInfo(currentPlayer);
		}

		return new GameDiceResult(dice1, dice2);
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

	public void proceedEvent(GameEventResultRequest gameEventResultRequest) {

	}
}
