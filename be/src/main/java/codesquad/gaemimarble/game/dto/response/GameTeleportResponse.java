package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameTeleportResponse {
	private final String playerId;
	private final Integer location;

	@Builder
	private GameTeleportResponse(String playerId, Integer location) {
		this.playerId = playerId;
		this.location = location;
	}
}
