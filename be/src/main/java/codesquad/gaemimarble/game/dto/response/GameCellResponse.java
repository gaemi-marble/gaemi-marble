package codesquad.gaemimarble.game.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameCellResponse {
	private String playerId;
	private Integer location;
	private Integer salary;
	private Integer dividend;

	@Builder
	public GameCellResponse(String playerId, Integer location, Integer salary, Integer dividend) {
		this.playerId = playerId;
		this.location = location;
		this.salary = salary;
		this.dividend = dividend;
	}
}
