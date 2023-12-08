package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameAccessibleResponse {
	private final Boolean isPresent;
	private final Boolean isFull;
	private final Boolean isPlaying;

	@Builder
	private GameAccessibleResponse(Boolean isPresent, Boolean isFull, Boolean isPlaying) {
		this.isPresent = isPresent;
		this.isFull = isFull;
		this.isPlaying = isPlaying;
	}
}
