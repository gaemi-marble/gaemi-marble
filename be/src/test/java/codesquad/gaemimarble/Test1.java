package codesquad.gaemimarble;

import java.util.HashMap;
import java.util.Map;

import codesquad.gaemimarble.game.entity.Player;
import org.junit.jupiter.api.Test;

public class Test1 {
	@Test
	void map() {
		Map<Integer, Integer> map = new HashMap<>();
		Integer a = map.get(1);
		System.out.println(a);
	}


	@Test
	void random() {
		int randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
		randomIndex = (int)(Math.random() * 2) + 1;
		System.out.println(randomIndex);
	}
}
