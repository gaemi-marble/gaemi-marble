package codesquad.gaemimarble.game.entity;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.scanner.Constant;

import codesquad.gaemimarble.util.Constants;
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
	private List<Events> selectedEvents;
	private Integer sellingTime;

	@Builder
	private GameStatus(Boolean isStarted, List<Player> players, List<Stock> stocks, Board board, Integer roundCount,
		CurrentPlayerInfo currentPlayerInfo, List<Events> selectedEvents, Integer sellingTime) {
		this.isStarted = isStarted;
		this.players = players;
		this.stocks = stocks;
		this.board = board;
		this.roundCount = roundCount;
		this.currentPlayerInfo = currentPlayerInfo;
		this.selectedEvents = selectedEvents;
		this.sellingTime = sellingTime;
	}

	public void setOrder(Integer firstOrder) {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setOrder(((i + players.size() - firstOrder + 1) % players.size()) + 1);
			log.info("설정된 오더 (아이디 + 오더)" + players.get(i).getPlayerId() + "/" + players.get(i).getOrder());
		}
	}

	public void waitSellingTime() {
		for (int i = 0; i < sellingTime; i++) {
			try {
				Thread.sleep(1_000);
				this.sellingTime--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sellingTime = Constants.SELLING_TIME;
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

	public void incrementRoundCount() {
		roundCount++;
	}
}
