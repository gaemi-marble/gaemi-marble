package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameTeleportRequest {
	private Long gameId;
	private String playerId;
	private Integer location;

	@Builder
	public GameTeleportRequest(Long gameId, String playerId, Integer location) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.location = location;
	}
}
