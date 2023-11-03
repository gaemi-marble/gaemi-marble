package codesquad.gaemimarble.game.entity;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
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
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setOrder(((i + players.size() - firstOrder + 1) % players.size()) + 1);
			log.info("설정된 오더 (아이디 + 오더)" + players.get(i).getPlayerId() + "/" + players.get(i).getOrder());
		}
	}
	// players.get(firstOrder-1).setOrder(1);
	// 	for (int i = firstOrder; i < players.size() + firstOrder; i++) {
	// 	players.get(i).setOrder(players.get(i-1).getOrder() + 1);
	// 	log.info("설정된 오더 (아이디 + 오더)" + players.get(i).getPlayerId() + "/" + players.get(i).getOrder());
	// }

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
