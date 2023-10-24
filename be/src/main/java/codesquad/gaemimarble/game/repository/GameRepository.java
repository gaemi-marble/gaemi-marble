package codesquad.gaemimarble.game.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import codesquad.gaemimarble.game.entity.GameStatus;
import codesquad.gaemimarble.game.entity.Player;

@Repository
public class GameRepository {
	private final ConcurrentMap<Long, GameStatus> gameStatusMap = new ConcurrentHashMap<>();
	private final AtomicLong gameId = new AtomicLong(0);

	public Long createRoom(GameStatus gameStatus) {
		Long gameRoomId = gameId.getAndAdd(1L);
		gameStatusMap.put(gameRoomId, gameStatus);
		return gameRoomId;
	}

	public List<Player> enterGame(Long gameId, Player player) {
		List<Player> players = gameStatusMap.get(gameId).getPlayers();
		player.setOrder(players.size() + 1);
		players.add(player);
		return players;
	}

	public List<Player> getAllPlayer(Long gameId) {
		return gameStatusMap.get(gameId).getPlayers();
	}

	public GameStatus getGameStatus(Long gameId) {
		return gameStatusMap.get(gameId);
	}
}
