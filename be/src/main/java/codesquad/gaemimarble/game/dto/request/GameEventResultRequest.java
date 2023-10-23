package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEventResultRequest {
	private String type;
	private Long gameId;
	private String eventName;

	@Builder
	private GameEventResultRequest(String type, Long gameId, String eventName) {
		this.type = type;
		this.gameId = gameId;
		this.eventName = eventName;
	}
}
