package codesquad.gaemimarble.game.entity.ground;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoodNews implements Cell {
	private final Integer location;
}
