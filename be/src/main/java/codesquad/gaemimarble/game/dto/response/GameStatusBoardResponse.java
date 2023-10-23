package codesquad.gaemimarble.game.dto.response;

import java.util.List;

import lombok.Builder;

public class GameStatusBoardResponse {
	private final List<GameStockStatusResponse> stockStatusBoard;

	@Builder
	public GameStatusBoardResponse(List<GameStockStatusResponse> stockStatusBoard) {
		this.stockStatusBoard = stockStatusBoard;
	}
}
