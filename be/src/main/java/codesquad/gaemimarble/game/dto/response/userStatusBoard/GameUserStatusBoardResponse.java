package codesquad.gaemimarble.game.dto.response.userStatusBoard;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameUserStatusBoardResponse {
	private final Integer cashAsset;
	private final Integer stockAsset;
	private final Integer totalAsset;
	List<StockResponse> stockList;

	@Builder
	public GameUserStatusBoardResponse(Integer cashAsset, Integer stockAsset, Integer totalAsset,
		List<StockResponse> stockList) {
		this.cashAsset = cashAsset;
		this.stockAsset = stockAsset;
		this.totalAsset = totalAsset;
		this.stockList = stockList;
	}
}
