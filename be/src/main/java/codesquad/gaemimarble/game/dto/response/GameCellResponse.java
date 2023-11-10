package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameCellResponse {
	private String playerId;
	private Integer location;

	@Builder
	public GameCellResponse(String playerId, Integer location) {
		this.playerId = playerId;
		this.location = location;
	}
}
