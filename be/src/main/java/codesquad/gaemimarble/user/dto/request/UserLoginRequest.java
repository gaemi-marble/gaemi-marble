package codesquad.gaemimarble.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginRequest {
	private String playerId;
	private String password;

	@Builder
	public UserLoginRequest(String playerId, String password) {
		this.playerId = playerId;
		this.password = password;
	}
}
