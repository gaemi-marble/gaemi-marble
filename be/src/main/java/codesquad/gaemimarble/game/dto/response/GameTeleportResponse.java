package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameTeleportResponse {
	private final Integer location;

	@Builder
	private GameTeleportResponse(Integer location) {
		this.location = location;
	}
}
