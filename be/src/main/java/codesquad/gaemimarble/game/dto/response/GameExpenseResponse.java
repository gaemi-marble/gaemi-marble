package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameExpenseResponse {
	String playerId;
	Integer amount;

	@Builder
	private GameExpenseResponse(String playerId, Integer amount) {
		this.playerId = playerId;
		this.amount = amount;
	}
}
