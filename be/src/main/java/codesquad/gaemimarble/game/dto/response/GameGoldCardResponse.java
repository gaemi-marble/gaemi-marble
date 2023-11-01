package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameGoldCardResponse {
	private String title;
	private String description;

	@Builder
	private GameGoldCardResponse(String title, String description) {
		this.title = title;
		this.description = description;
	}
}
