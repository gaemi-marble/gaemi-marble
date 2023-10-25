package codesquad.gaemimarble.game.dto.response.userStatusBoard;

import java.util.List;

import lombok.Builder;

public class GameUserStatusBoardResponse {
	private final Integer cashAsset;
	private final Integer stockAsset;
	private final Integer totalAsset;
	List<StockNameResponse> stockList;

	@Builder
	public GameUserStatusBoardResponse(Integer cashAsset, Integer stockAsset, Integer totalAsset,
		List<StockNameResponse> stockList) {
		this.cashAsset = cashAsset;
		this.stockAsset = stockAsset;
		this.totalAsset = totalAsset;
		this.stockList = stockList;
	}
}
