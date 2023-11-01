package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GamePrisonDiceResponse {
	private String playerId;
	private Integer dice1;
	private Integer dice2;
	private Boolean hasEscaped;

	@Builder
	private GamePrisonDiceResponse(String playerId, Integer dice1, Integer dice2, Boolean hasEscaped) {
		this.playerId = playerId;
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.hasEscaped = hasEscaped;
	}
}
