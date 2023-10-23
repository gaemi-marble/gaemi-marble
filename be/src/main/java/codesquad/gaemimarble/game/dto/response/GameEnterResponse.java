package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEnterResponse {
	private Integer order;
	private String playerId;

	@Builder
	public GameEnterResponse(Integer order, String playerId) {
		this.order = order;
		this.playerId = playerId;
	}

	public static GameEnterResponse of(Integer order, String playerId) {
		return GameEnterResponse.builder()
			.order(order)
			.playerId(playerId)
			.build();
	}
}
