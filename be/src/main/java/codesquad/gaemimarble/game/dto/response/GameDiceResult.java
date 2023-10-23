package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameDiceResult {
	private Integer dice1;
	private Integer dice2;

	@Builder
	public GameDiceResult(Integer dice1, Integer dice2) {
		this.dice1 = dice1;
		this.dice2 = dice2;
	}
}
