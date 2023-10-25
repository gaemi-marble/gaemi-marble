package codesquad.gaemimarble.game.dto.response.userStatusBoard;

import lombok.Builder;

public class GameUserBoardResponse {
	private final String playerId;

	private final GameUserStatusBoardResponse userStatusBoard;

	@Builder
	private GameUserBoardResponse(String playerId, GameUserStatusBoardResponse userStatusBoard) {
		this.playerId = playerId;
		this.userStatusBoard = userStatusBoard;
	}
}
