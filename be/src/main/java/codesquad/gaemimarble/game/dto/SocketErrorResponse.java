package codesquad.gaemimarble.game.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocketErrorResponse {
	private String errorType; // 나중에 에러 코드로 변경
	private String message;

	@Builder
	public SocketErrorResponse(String errorType, String message) {
		this.errorType = errorType;
		this.message = message;
	}
}
