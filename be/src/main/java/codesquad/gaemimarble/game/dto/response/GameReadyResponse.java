package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameReadyResponse {
	private String playerId;
	private boolean isReady;

	@Builder
	public GameReadyResponse(String playerId, boolean isReady) {
		this.playerId = playerId;
		this.isReady = isReady;
	}
}
