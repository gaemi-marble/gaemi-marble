package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;

public class GameEndTurnResponse {
	private final String nextPlayerId;

	@Builder
	public GameEndTurnResponse(String nextPlayerId) {
		this.nextPlayerId = nextPlayerId;
	}
}
