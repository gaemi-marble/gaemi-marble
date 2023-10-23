package codesquad.gaemimarble.game.entity;

import lombok.Getter;

@Getter
public enum Share {
	APPLE("APPLE", Theme.IT, 2_000_000),
	STARK_INDUSTRY("STARK_INDUSTRY", Theme.MILITARY, 1_900_000),
	TESLA("TESLA", Theme.ELON_MUSK, 1_800_000),
	HERMES("HERMES", Theme.FASHION, 1_700_000),
	HYUNDAI("HYUNDAI", Theme.CONSTRUCTION, 1_600_000),
	KOREAN_AIR("KOREAN_AIR", Theme.TRIP, 1_500_000),
	MCDONALDS("MCDONALDS", Theme.FOOD, 1_400_000),
	SAMSUNG_BIO("SAMSUNG_BIO", Theme.PHARMACEUTICAL, 1_300_000),
	GOOGLE("GOOGLE", Theme.IT, 1_200_000),
	TWITTER("TWITTER", Theme.ELON_MUSK, 1_100_000),
	PFIZER("PFIZER", Theme.PHARMACEUTICAL, 1_000_000),
	HANWHA_DEFENSE("HANWHA_DEFENSE", Theme.MILITARY, 900_000),
	CODE_SQUAD("CODE_SQUAD", Theme.IT, 800_000),
	GS("GS", Theme.CONSTRUCTION, 700_000),
	HANA_TOUR("HANA_TOUR", Theme.TRIP, 600_000),
	NONGSHIM("NONGSHIM", Theme.FOOD, 500_000),
	MUSINSA("MUSINSA", Theme.FASHION, 400_000);

	private final String name;
	private final Theme theme;
	private final Integer startPrice;

	Share(String name, Theme theme, Integer startPrice) {
		this.name = name;
		this.theme = theme;
		this.startPrice = startPrice;
	}
}
