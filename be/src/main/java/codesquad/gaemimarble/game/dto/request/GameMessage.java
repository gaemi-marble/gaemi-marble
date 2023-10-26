package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameMessage {
	private String type;

	@Builder
	public GameMessage(String type) {
		this.type = type;
	}
}
