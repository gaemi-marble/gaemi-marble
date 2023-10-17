package codesquad.gaemimarble.game;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameMessage {
	private final Long gameId;
	private final String type;

	@Builder
	public GameMessage(Long gameId, String type) {
		this.gameId = gameId;
		this.type = type;
	}
}
