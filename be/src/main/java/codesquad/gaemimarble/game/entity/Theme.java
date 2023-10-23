package codesquad.gaemimarble.game.entity;

import java.util.List;

import lombok.Getter;

@Getter
public enum Theme {
	IT("IT", List.of(Share.APPLE, Share.GOOGLE, Share.CODE_SQUAD)),
	MILITARY("Military", List.of(Share.STARK_INDUSTRY, Share.HANWHA_DEFENSE)),
	FOOD("Food", List.of(Share.NONGSHIM, Share.MCDONALDS)),
	ELON_MUSK("Elon Musk", List.of(Share.TESLA, Share.TWITTER)),
	FASHION("Fashion", List.of(Share.HERMES, Share.MUSINSA)),
	TRIP("Trip", List.of(Share.KOREAN_AIR, Share.HANA_TOUR)),
	PHARMACEUTICAL("Pharmaceutical", List.of(Share.PFIZER, Share.SAMSUNG_BIO)),
	CONSTRUCTION("Construction", List.of(Share.GS, Share.HYUNDAI));

	private final String name;
	private final List<Share> shares;

	Theme(String name, List<Share> shares) {
		this.name = name;
		this.shares = shares;
	}
}
