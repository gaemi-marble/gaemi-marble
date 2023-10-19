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
		player.setOrder(gameStatusMap.get(gameId).getPlayers().size() + 1);
		gameStatusMap.get(gameId).getPlayers().add(player);
		return gameStatusMap.get(gameId).getPlayers();
	}
}
