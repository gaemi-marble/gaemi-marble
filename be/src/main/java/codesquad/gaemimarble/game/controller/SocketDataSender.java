package codesquad.gaemimarble.game.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.gaemimarble.exception.PlayTimeException;
import codesquad.gaemimarble.exception.SocketException;
import codesquad.gaemimarble.game.dto.ResponseDTO;
import codesquad.gaemimarble.game.dto.SocketErrorResponse;
import codesquad.gaemimarble.game.entity.TypeConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketDataSender {
	private final ConcurrentMap<Long, ConcurrentMap<String, WebSocketSession>> gameSocketMap = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;

	public void createRoom(Long gameRoomId) {
		gameSocketMap.put(gameRoomId, new ConcurrentHashMap<>());
	}

	public boolean saveSocket(Long gameId, String playerId, WebSocketSession session) {
		ConcurrentMap<String, WebSocketSession> socketMap = gameSocketMap.get(gameId);
		if (socketMap.containsKey(playerId)) {
			socketMap.put(playerId, session);
			return true;
		}

		if (socketMap.values().size() >= 4) {
			throw new SocketException("인원이 가득 찼습니다.", playerId, gameId, session);
		}
		return false;
	}

	public <T> void send(Long gameId, T object) {
		for (WebSocketSession session : gameSocketMap.get(gameId).values()) {
			try {
				session.sendMessage(new TextMessage(objectMapper.writeValueAsString(object)));
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		System.out.println("전송 완료");
	}

	public void sendErrorMessage(Long gameId, String playerId, String message) {
		try {
			if (playerId == null) {
				send(gameId, new ResponseDTO<>(TypeConstants.ERROR, new SocketErrorResponse(message)));
				return;
			}
			gameSocketMap.get(gameId).get(playerId).sendMessage(new TextMessage(objectMapper.writeValueAsString(
				new ResponseDTO<>(TypeConstants.ERROR, new SocketErrorResponse(message)))));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
