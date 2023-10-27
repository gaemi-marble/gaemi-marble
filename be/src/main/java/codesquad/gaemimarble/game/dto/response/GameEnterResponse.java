package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameEnterResponse {
	private Integer order;
	private String playerId;
	private Boolean isReady;

	@Builder
	public GameEnterResponse(Integer order, String playerId, Boolean isReady) {
		this.order = order;
		this.playerId = playerId;
		this.isReady = isReady;
	}

	public static GameEnterResponse of(Integer order, String playerId, Boolean isReady) {
		return GameEnterResponse.builder()
			.order(order)
			.playerId(playerId)
			.isReady(isReady)
			.build();
	}
}
