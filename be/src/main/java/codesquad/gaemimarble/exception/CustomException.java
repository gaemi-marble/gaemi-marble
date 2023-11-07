package codesquad.gaemimarble.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final String playerId;
	private final Long gameId;

	public CustomException(String message, String playerId, Long gameId) {
		super(message);
		this.playerId = playerId;
		this.gameId = gameId;
	}
}
