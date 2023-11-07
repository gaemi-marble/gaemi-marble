package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlayerAsset {
	private final String playerId;
	private final Integer totalAsset;

	@Builder
	private PlayerAsset(String playerId, Integer totalAsset) {
		this.playerId = playerId;
		this.totalAsset = totalAsset;
	}
}
