package codesquad.gaemimarble.game.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketDataSender {
	private final ConcurrentMap<Long, Set<WebSocketSession>> gameSocketMap = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;

	public void createRoom(Long gameRoomId) {
		gameSocketMap.put(gameRoomId, new HashSet<>());
	}

	public void saveSocket(Long gameId, WebSocketSession session) {
		gameSocketMap.get(gameId).add(session);
	}

	public <T> void send(Long gameId, T object) {
		for (WebSocketSession session : gameSocketMap.get(gameId)) {
			try {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(object)));
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		System.out.println("전송 완료");
	}
}
