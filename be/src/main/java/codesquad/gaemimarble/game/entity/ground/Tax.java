package codesquad.gaemimarble.game.entity.ground;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tax implements Cell {
	private final Integer location;
}
