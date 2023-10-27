package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameEventNameResponse {
	private final String name;

	@Builder
	private GameEventNameResponse(String name) {
		this.name = name;
	}
}
