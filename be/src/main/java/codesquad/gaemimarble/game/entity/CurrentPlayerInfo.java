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
	private Boolean rolledDouble;
	private Boolean hasMoved;

	@Builder
	private CurrentPlayerInfo(String playerId, Integer order) {
		this.playerId = playerId;
		this.order = order;
		this.countDouble = 0;
		this.rolledDouble = false;
		this.hasMoved = false;
	}

	public int increaseCountDouble() {
		rolledDouble = true;
		return this.countDouble += 1;
	}

	public void update(Player player) {
		this.playerId = player.getPlayerId();
		this.order = player.getOrder();
		this.countDouble = 0;
		this.rolledDouble = false;
	}
	public void move(){
		hasMoved = true;
	}
	public void resetMove() {
		hasMoved = false;
	}

	public void resetCountDouble() {
		this.countDouble = 0;
	}

	public void initRolledDouble() {
		rolledDouble = false;
	}


}
