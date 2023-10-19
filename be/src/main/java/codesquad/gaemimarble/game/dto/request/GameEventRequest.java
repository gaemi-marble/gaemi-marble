package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEventRequest {
	private Long gameId;

	@Builder
	private GameEventRequest(Long gameId) {
		this.gameId = gameId;
	}
}
