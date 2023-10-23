package codesquad.gaemimarble.game.entity;

import java.util.List;

import lombok.Getter;

@Getter
public enum Theme {
	IT("it", List.of(Share.APPLE, Share.GOOGLE, Share.CODE_SQUAD)),
	MILITARY("military", List.of(Share.STARK_INDUSTRY, Share.HANWHA_DEFENSE)),
	FOOD("food", List.of(Share.NONGSHIM, Share.MCDONALDS)),
	ELON_MUSK("elonmusk", List.of(Share.TESLA, Share.TWITTER)),
	FASHION("fashion", List.of(Share.HERMES, Share.MUSINSA)),
	TRIP("Trip", List.of(Share.KOREAN_AIR, Share.HANA_TOUR)),
	PHARMACEUTICAL("pharmaceutical", List.of(Share.PFIZER, Share.SAMSUNG_BIO)),
	CONSTRUCTION("construction", List.of(Share.GS, Share.HYUNDAI));

	private final String name;
	private final List<Share> shares;

	Theme(String name, List<Share> shares) {
		this.name = name;
		this.shares = shares;
	}
}
