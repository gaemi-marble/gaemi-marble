package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEmotionResponse {
	private String playerId;
	private String name;

	@Builder
	public GameEmotionResponse(String playerId, String name) {
		this.playerId = playerId;
		this.name = name;
	}
}
