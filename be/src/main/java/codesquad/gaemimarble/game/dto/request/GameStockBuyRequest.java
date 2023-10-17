package codesquad.gaemimarble.game.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameStockBuyRequest {
	private String stockName;
	private Long count;

	public GameStockBuyRequest(String stockName, Long count) {
		this.stockName = stockName;
		this.count = count;
	}
}
