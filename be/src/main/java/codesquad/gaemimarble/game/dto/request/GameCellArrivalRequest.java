package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameCellArrivalRequest {
	private final Long gameId;
	private final String playerId;

	@Builder
	public GameCellArrivalRequest(Long gameId, String playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
	}
}
