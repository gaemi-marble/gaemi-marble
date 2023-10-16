package codesquad.gaemimarble.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignUpRequest {
	private final String playerId;
	private final String password;

	@Builder
	public UserSignUpRequest(String playerId, String password) {
		this.playerId = playerId;
		this.password = password;
	}
}
