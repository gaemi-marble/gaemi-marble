package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GamePrisonDiceResponse {
	private Integer dice1;
	private Integer dice2;
	private Boolean hasEscaped;

	@Builder
	private GamePrisonDiceResponse(Integer dice1, Integer dice2, Boolean hasEscaped) {
		this.dice1 = dice1;
		this.dice2 = dice2;
		this.hasEscaped = hasEscaped;
	}
}
