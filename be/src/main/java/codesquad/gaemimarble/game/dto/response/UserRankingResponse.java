package codesquad.gaemimarble.game.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRankingResponse {
	List<PlayerAsset> ranking;

	@Builder
	private UserRankingResponse(List<PlayerAsset> ranking) {
		this.ranking = ranking;
	}
}
