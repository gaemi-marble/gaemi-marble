package codesquad.gaemimarble.game.player.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import codesquad.gaemimarble.game.player.entity.Player;
import lombok.Getter;

@Repository
public class PlayerRepository {
	@Getter
	private final ConcurrentMap<Long, List<Player>> playerMap = new ConcurrentHashMap<>();

	public List<Player> enterGame(Long gameId, Player player) {
		List<Player> players = playerMap.get(gameId);
		int order = 1;
		for (Player p : players) {
			if (Objects.equals(p.getOrder(), order)) {
				order += 1;
			} else {
				break;
			}
		}
		player.setOrder(order);
		players.add(player);
		players.sort((p1, p2) -> p1.getOrder().compareTo(p2.getOrder()));
		return players;
	}

	public void createPlayers(Long gameId) {
		playerMap.put(gameId, new ArrayList<>());
	}

	public List<Player> getAllPlayer(Long gameId) {
		return playerMap.get(gameId);
	}

	public Player getPlayer(Long gameId, String playerId) {
		return getAllPlayer(gameId)
			.stream()
			.filter(p -> p.getPlayerId().equals(playerId))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("해당 플레이어가 존재하지 않습니다"));
	}

	public List<Player> leaveGame(Long gameId, String playerId) {
		List<Player> players = getAllPlayer(gameId);
		players.remove(getPlayer(gameId, playerId));
		return players;
	}
}
