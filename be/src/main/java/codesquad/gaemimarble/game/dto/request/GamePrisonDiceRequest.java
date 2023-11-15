package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GamePrisonDiceRequest {
	private Long gameId;
	private String playerId;

	@Builder
	public GamePrisonDiceRequest(Long gameId, String playerId) {
		this.gameId = gameId;
		this.playerId = playerId;
	}
}
