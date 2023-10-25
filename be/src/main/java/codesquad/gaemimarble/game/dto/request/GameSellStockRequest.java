package codesquad.gaemimarble.game.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameSellStockRequest {
	private Long gameId;
	private String playerId;
	private List<StockNameQuantity> stockList;

	@Builder
	private GameSellStockRequest(Long gameId, String playerId, List<StockNameQuantity> stockList) {
		this.gameId = gameId;
		this.playerId = playerId;
		this.stockList = stockList;
	}
}
