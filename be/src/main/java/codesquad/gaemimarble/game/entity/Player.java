package codesquad.gaemimarble.game.entity;

import java.util.HashMap;
import java.util.Map;

import codesquad.gaemimarble.game.dto.response.GameEnterResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Player {
	private String playerId;
	private Integer order;
	private Integer location;
	private Map<String, Integer> myStocks;
	private Integer stockAsset;
	private Integer cashAsset;
	private Integer totalAsset;
	private Boolean isReady;
	private Integer prisonCount;
	// 상태, 황금카드 보류

	@Builder
	Player(String playerId, Integer order, Integer location, Map<String, Integer> myStocks, Integer stockAsset,
		Integer cashAsset, Integer totalAsset, Boolean isReady, Integer prisonCount) {
		this.playerId = playerId;
		this.order = order;
		this.location = location;
		this.myStocks = myStocks;
		this.stockAsset = stockAsset;
		this.cashAsset = cashAsset;
		this.totalAsset = totalAsset;
		this.isReady = isReady;
		this.prisonCount = prisonCount;
	}

	public static Player init(String playerId) {
		return Player.builder()
			.playerId(playerId)
			.cashAsset(200_000_000)
			.location(0)
			.myStocks(new HashMap<>())
			.stockAsset(0)
			.totalAsset(200_000_000)
			.isReady(false)
			.prisonCount(0)
			.build();
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public void addAsset(int cashAsset, int stockAsset) {
		this.cashAsset += cashAsset;
		this.stockAsset += stockAsset;
		this.totalAsset = this.cashAsset + this.stockAsset;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public void move(int i) {
		this.location += i;
	}

	public void goToPrison() {
		this.location = 6;
	}

	public void buy(Stock stock, Integer quantity) {
		myStocks.put(stock.getName(), myStocks.getOrDefault(stock.getName(), 0) + quantity);
		cashAsset -= quantity * stock.getCurrentPrice();
		stockAsset += quantity * stock.getCurrentPrice();
	}

	public void sellStock(Stock stock, Integer quantity) {
		myStocks.put(stock.getName(), myStocks.get(stock.getName()) - quantity);
		cashAsset += quantity * stock.getCurrentPrice();
		stockAsset -= quantity * stock.getCurrentPrice();
	}

	public void updateStockAsset(Stock stock) {
		stockAsset = myStocks.get(stock.getName()) * stock.getCurrentPrice();
		totalAsset = cashAsset + stockAsset;
	}

	public void escapePrison(int diceResult) {
		this.prisonCount = 0;
		this.location += diceResult;
	}

	public void increasePrisonCount() {
		this.prisonCount += 1;
	}
}
