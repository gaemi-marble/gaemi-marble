package codesquad.gaemimarble.game.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Player {
	private String playerId;
	private Integer order;
	private Integer location;
	private List<Stock> myStocks;
	private Integer stockAsset;
	private Integer cashAsset;
	private Integer totalAsset;
	// 상태, 황금카드 보류

	@Builder
	Player(String playerId, Integer order, Integer location, List<Stock> myStocks, Integer stockAsset,
		Integer cashAsset,
		Integer totalAsset) {
		this.playerId = playerId;
		this.order = order;
		this.location = location;
		this.myStocks = myStocks;
		this.stockAsset = stockAsset;
		this.cashAsset = cashAsset;
		this.totalAsset = totalAsset;
	}

	public static Player init(String playerId) {
		return Player.builder()
			.playerId(playerId)
			.cashAsset(200_000_000)
			.location(0)
			.myStocks(new ArrayList<>())
			.stockAsset(0)
			.totalAsset(200_000_000)
			.build();
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public GameEnterResponse toDto() {
		return new GameEnterResponse(this.getOrder(), this.getPlayerId());
	}
}
