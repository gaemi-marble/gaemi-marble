package codesquad.gaemimarble.game.repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

@Repository
public class GameRepository {
	private final ConcurrentMap<Long, Set<WebSocketSession>> gameSocketMap = new ConcurrentHashMap<>();

	public void saveSocket(Long gameId, WebSocketSession session) {
		Set<WebSocketSession> sessionSet = gameSocketMap.getOrDefault(gameId, new HashSet<>());
		sessionSet.add(session);
		gameSocketMap.put(gameId, sessionSet);
	}
}
