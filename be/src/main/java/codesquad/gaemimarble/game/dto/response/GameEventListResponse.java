package codesquad.gaemimarble.game.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameEventListResponse {
	private final List<GameEventResponse> events;

	@Builder
	private GameEventListResponse(List<GameEventResponse> events) {
		this.events = events;
	}
}
