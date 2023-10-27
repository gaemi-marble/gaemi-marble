package codesquad.gaemimarble.game.dto.response.userStatusBoard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StockResponse {
	private final String name;
	private final Integer quantity;

	@Builder
	private StockResponse(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}
}
