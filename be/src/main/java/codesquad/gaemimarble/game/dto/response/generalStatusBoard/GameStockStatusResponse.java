package codesquad.gaemimarble.game.dto.response.generalStatusBoard;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameStockStatusResponse {
	private final String theme;
	private final String name;
	private final Integer price;
	private final Integer quantity;

	@Builder
	private GameStockStatusResponse(String theme, String name, Integer price, Integer quantity) {
		this.theme = theme;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
}
