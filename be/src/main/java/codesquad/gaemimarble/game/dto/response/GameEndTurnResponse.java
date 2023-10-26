package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class GameEndTurnResponse {
	private final String nextPlayerId;

	@Builder
	public GameEndTurnResponse(String nextPlayerId) {
		this.nextPlayerId = nextPlayerId;
	}
}
