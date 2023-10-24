package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameReadyResponse {
	private String playerId;
	private Boolean isReady;

	@Builder
	public GameReadyResponse(String playerId, Boolean isReady) {
		this.playerId = playerId;
		this.isReady = isReady;
	}
}
