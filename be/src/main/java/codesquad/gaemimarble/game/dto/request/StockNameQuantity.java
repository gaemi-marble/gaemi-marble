package codesquad.gaemimarble.game.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockNameQuantity {
	private String name;
	private Integer quantity;

	@Builder
	private StockNameQuantity(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}
}
