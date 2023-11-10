package codesquad.gaemimarble.game.entity;

import lombok.Getter;

@Getter
public enum GoldCard {
	ROB("강탈", "선택한 플레이어에게서 1000만원을 강탈한다."),
	DONATION("기부", "선택한 플레이어에게 1000만원을 기부한다."),

	private final String title;
	private final String description;

	GoldCard(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public static GoldCard getRandomGoldCard() {
		return GoldCard.values()[(int) (Math.random() * GoldCard.values().length)];
	}
}
