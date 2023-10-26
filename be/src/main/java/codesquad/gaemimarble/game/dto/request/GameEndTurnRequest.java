package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEndTurnRequest {
	private Long gameId;
	private String playerId;

	@Builder
	private GameEndTurnRequest(Long gameId, String playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
	}
}
