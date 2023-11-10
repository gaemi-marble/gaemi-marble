package codesquad.gaemimarble.game.dto.request.GoldCardRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameDonationRequest {
	private Long gameId;
	private String playerId;
	private String receiverId;

	@Builder
	private GameDonationRequest(Long gameId, String playerId, String receiverId) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.receiverId = receiverId;
	}
}
