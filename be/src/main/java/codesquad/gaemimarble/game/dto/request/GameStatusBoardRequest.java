package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameStatusBoardRequest {
	private final Long gameId;

	@Builder
	public GameStatusBoardRequest(Long gameId) {
		this.gameId = gameId;
	}
}
