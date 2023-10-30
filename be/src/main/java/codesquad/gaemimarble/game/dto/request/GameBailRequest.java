package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameBailRequest {
	Long gameId;
	String playerId;

	@Builder
	public GameBailRequest(Long gameId, String playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
	}
}
