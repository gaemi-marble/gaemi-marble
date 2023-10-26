package codesquad.gaemimarble.game.entity;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameStatus {
	private final Boolean isStarted;
	private final List<Player> players;
	private final List<Stock> stocks;
	private final Board board;
	private Integer roundCount;
	private CurrentPlayerInfo currentPlayerInfo;

	@Builder
	private GameStatus(Boolean isStarted, List<Player> players, List<Stock> stocks, Board board, Integer roundCount,
		CurrentPlayerInfo currentPlayerInfo) {
		this.isStarted = isStarted;
		this.players = players;
		this.stocks = stocks;
		this.board = board;
		this.roundCount = roundCount;
		this.currentPlayerInfo = currentPlayerInfo;
	}

	public void setOrder(Integer firstOrder) {
		for (Player player : players) {
			player.setOrder(((player.getOrder() + (players.size() - firstOrder)) % players.size()) + 1);
		}
	}

	public void initCurrentPlayerInfo(Player player) {
		this.currentPlayerInfo = CurrentPlayerInfo.builder()
			.playerId(player.getPlayerId())
			.order(player.getOrder())
			.build();
	}

	public Player getPlayer(String playerId) {
		return this.players.stream()
			.filter(player -> player.getPlayerId().equals(playerId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당하는 플레이어가 없습니다."));
	}
}
