package codesquad.gaemimarble.game.entity;

import lombok.Getter;

@Getter
public enum Events {

	EPIDEMIC("역병", "새로운 바이러스의 탄생...", "제약 / 바이오 + 50%, 나머지- 30%"),
	EUREKA("유레카", "초전도체 발견 !", "IT + 50%"),
	WAR("전쟁", "전쟁 발발 !", "군수 + 50%, 여행 -30%"),
	GREAT_BOOM("역대급 불장", "영차 영차 !!", "모든 기업 + 50%"),
	GREAT_BUST("역대급 물장", "돔황챠 !!", "모든 기업 - 30%"),
	DOGECOIN("도지 화성 갈끄니까", "이 사기꾼 또 뻘글", "일론 머스크 - 20%"),
	AUTONOMOUS_DRIVING("자율 주행 한단계 더", "일론머스크는 천재다", "일론 머스크 + 20%"),
	NATURAL_DISASTER("자연재해", "무친 태풍", "건설 + 30%, 여행 - 20%"),
	WEATHER_CONTROL("날씨 조타", "여행 가기 딱 좋은 날씨군요", "여행 + 30%, 패션 +20%"),
	INNOVATIVE_PRODUCT("혁신적인 신제품 발표", "IT 대기업들에서 신제품 발표가 성공적으로 이루어졌다.", "IT 관련주 + 20%"),
	NEW_DRUG_SUCCESS("신약개발 성공/부작용 발생", "탈모 치료제가 나왔다고?", "제약/바이오 관련주 +50%/ -20%"),
	INTEREST_RATE_HIKE("금리 인상", "대출 비용 증가, 소비 부담 증가", "모든 주식 - 10%"),
	LOW_INTEREST_POLICY("저금리 정책 발표", "대출 확대, 투자 촉진", "모든 주식 + 20%"),
	ECONOMIC_BOOM("경기 호황", "경제가 성장한다!", "모든 주식 + 30%"),
	LARGE_SCALE_HACKING("대규모 해킹 사건 발생", "개인 정보 해킹 당함, 보안 문제", "IT 주식 - 30%"),
	TRAVEL_ALERT_LIFTED("여행 경보 해제", "여행 붐", "여행 + 40%"),
	CONSUMER_SENTIMENT("소비심리", "패션관련 소비가 증가하고 있다.", "패션 관련주 +"),
	ELON_VS_ZUCKERBERG("일론머스크 vs 저커버그", "1라운드 뒤에 둘이 스파링 뜬다고 한다. 누가이길지는 모른다.", "승리: + 20%, 패배: - 20%"),
	WAR_OUTCOME("전쟁의 기류", "N 라운드 뒤에 전쟁이 일어날 확률 / 일어나지 않을 확률", "전쟁 발발: 군수 업, 전쟁 X: 군수 다운"),
	KOREAN_AIR_STRIKE("대한항공, 하나투어 파업", "20일 동안 대규모 파업", "여행 - 20%"),
	SUCCESSFUL_MCDONALDS_COLLAB("성공적인 맥도날드, 농심 콜라보", "신제품 버거 출시", "식품산업 + 30%");

	private final String title;
	private final String contents;
	private final String impact;

	Events(String title, String contents, String impact) {
		this.title = title;
		this.contents = contents;
		this.impact = impact;
	}
}
