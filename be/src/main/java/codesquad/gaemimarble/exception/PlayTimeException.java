package codesquad.gaemimarble.exception;

import lombok.Getter;

@Getter
public class PlayTimeException extends RuntimeException {
	private final String playerId;
	private final Long gameId;

	public PlayTimeException(String message, String playerId, Long gameId) {
		super(message);
		this.playerId = playerId;
		this.gameId = gameId;
	}
}
