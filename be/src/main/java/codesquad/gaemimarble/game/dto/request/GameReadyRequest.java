package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameReadyRequest {
	private Long gameId;
	private String playerId;
	private Boolean isReady;

	@Builder
	public GameReadyRequest(Long gameId, String playerId, Boolean isReady) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.isReady = isReady;
	}
}
