package codesquad.gaemimarble.game.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEventResultRequest {
	private Long gameId;
	private List<String> events;

	@Builder
	private GameEventResultRequest(Long gameId, List<String> events) {
		this.gameId = gameId;
		this.events = events;
	}
}
