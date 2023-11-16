package codesquad.gaemimarble.game.entity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import codesquad.gaemimarble.util.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class GameStatus {
	private final Boolean isStarted;
	private final List<Stock> stocks;
	private final Board board;
	private Integer roundCount;
	private CurrentPlayerInfo currentPlayerInfo;
	private List<Events> selectedEvents;
	private Integer sellingTime;

	@Builder
	private GameStatus(Boolean isStarted, List<Stock> stocks, Board board, Integer roundCount,
		CurrentPlayerInfo currentPlayerInfo, List<Events> selectedEvents, Integer sellingTime) {
		this.isStarted = isStarted;
		this.stocks = stocks;
		this.board = board;
		this.roundCount = roundCount;
		this.currentPlayerInfo = currentPlayerInfo;
		this.selectedEvents = selectedEvents;
		this.sellingTime = sellingTime;
	}

	public void waitSellingTime() {
		int time = sellingTime + 1;
		for (int i = 0; i < time; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
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

	public void incrementRoundCount() {
		roundCount++;
	}
}
