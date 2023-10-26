package codesquad.gaemimarble.game.dto.response.generalStatusBoard;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameStatusBoardResponse {
	private final List<GameStockStatusResponse> stockStatusBoard;

	@Builder
	public GameStatusBoardResponse(List<GameStockStatusResponse> stockStatusBoard) {
		this.stockStatusBoard = stockStatusBoard;
	}
}
