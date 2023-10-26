package codesquad.gaemimarble.game.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Stock {
	private Theme theme;
	private String name;
	private Integer startPrice;
	private Integer currentPrice;
	private Integer remainingStock;

	@Builder
	private Stock(Theme theme, String name, Integer startPrice, Integer currentPrice, Integer remainingStock) {
		this.theme = theme;
		this.name = name;
		this.startPrice = startPrice;
		this.currentPrice = currentPrice;
		this.remainingStock = remainingStock;
	}

	public Stock(Share share) {
		this.theme = share.getTheme();
		this.name = share.getName();
		this.startPrice = share.getStartPrice();
		this.currentPrice = share.getStartPrice();
		this.remainingStock = 100;
	}

	public static List<Stock> initStocks() {
		List<Stock> stocks = new ArrayList<>();
		for (Share share : Share.values()) {
			stocks.add(new Stock(share));
		}
		return stocks;
	}

	public void changePrice(Integer percentage) {
		this.currentPrice += ((this.startPrice * percentage) / 100);
	}

	public void decrementQuantity(Integer quantity) {
		remainingStock -= quantity;
	}

	public void incrementQuantity(Integer quantity) {
		remainingStock += quantity;
	}
}
