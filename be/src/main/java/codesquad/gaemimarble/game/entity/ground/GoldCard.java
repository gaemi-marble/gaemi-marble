package codesquad.gaemimarble.game.entity.ground;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoldCard implements Cell {
	private final Integer location;
}
