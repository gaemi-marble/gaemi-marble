package codesquad.gaemimarble.game.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocketErrorResponse {
	private String message;

	@Builder
	public SocketErrorResponse(String message) {
		this.message = message;
	}
}
