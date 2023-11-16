package codesquad.gaemimarble.game.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import codesquad.gaemimarble.game.entity.GameStatus;

@Repository
public class GameRepository {
	private final ConcurrentMap<Long, GameStatus> gameStatusMap = new ConcurrentHashMap<>();
	private final AtomicLong gameId = new AtomicLong(0);

	public Long createRoom(GameStatus gameStatus) {
		Long gameRoomId = gameId.getAndAdd(1L);
		gameStatusMap.put(gameRoomId, gameStatus);
		return gameRoomId;
	}

	public GameStatus getGameStatus(Long gameId) {
		return gameStatusMap.get(gameId);
	}
}
