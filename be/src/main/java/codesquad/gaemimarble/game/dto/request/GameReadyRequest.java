package codesquad.gaemimarble.game.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameReadyRequest {
	private Boolean isReady;

	public GameReadyRequest(Boolean isReady) {
		this.isReady = isReady;
	}
}
