package codesquad.gaemimarble.game.entity.ground;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Teleport implements Cell {
	private final Integer location;
}
