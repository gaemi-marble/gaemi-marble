package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRoomCreateResponse {
	private final Long gameId;
}
