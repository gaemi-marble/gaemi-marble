package codesquad.gaemimarble.game.dto.request.GoldCardRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameArrestRequest {
	private Long gameId;
	private String targetId;

	@Builder
	public GameArrestRequest(Long gameId, String targetId) {
		this.gameId = gameId;
		this.targetId = targetId;
	}
}
