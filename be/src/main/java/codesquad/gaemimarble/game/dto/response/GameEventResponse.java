package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;

public class GameEventResponse {
	private final String title;
	private final String content;
	private final String impact;

	@Builder
	private GameEventResponse(String title, String content, String impact) {
		this.title = title;
		this.content = content;
		this.impact = impact;
	}
}
