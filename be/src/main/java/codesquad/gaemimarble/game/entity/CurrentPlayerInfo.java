package codesquad.gaemimarble.game.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CurrentPlayerInfo {
	private String playerId;
	private Integer order;
	private Integer countDouble;

	@Builder
	private CurrentPlayerInfo(String playerId, Integer order, Integer countDouble) {
		this.playerId = playerId;
		this.order = order;
		this.countDouble = countDouble;
	}

	public void increaseCountDouble() {
		this.countDouble += 1;
	}

	public void update(Player player) {
		this.playerId = player.getPlayerId();
		//this.order = player;
		this.countDouble = 0;
	}
}
