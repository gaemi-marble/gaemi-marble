package codesquad.gaemimarble.game.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.gaemimarble.game.dto.ResponseDTO;
import codesquad.gaemimarble.game.dto.SocketErrorResponse;
import codesquad.gaemimarble.game.entity.TypeConstants;
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

	public boolean saveSocket(Long gameId, String playerId, WebSocketSession session) {
		Set<WebSocketSession> sessions = gameSocketMap.computeIfAbsent(gameId, key -> ConcurrentHashMap.newKeySet());
		try {
			if (sessions.size() == 4) {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
					new ResponseDTO<>(TypeConstants.ERROR, new SocketErrorResponse("full", "인원이 가득 찼습니다.")))));
				return false;
			}

			boolean isDuplicate = sessions.stream()
				.anyMatch(s -> s.getAttributes().get("playerId").equals(playerId));

			if (!isDuplicate) {
				session.getAttributes().put("playerId", playerId);
				sessions.add(session);
				return true;
			} else {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(
					new ResponseDTO<>(TypeConstants.ERROR, new SocketErrorResponse("duplicate", "이미 접속한 플레이어입니다.")))));
				return false;
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return false;
		}
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
