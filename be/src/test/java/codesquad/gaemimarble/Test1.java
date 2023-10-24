package codesquad.gaemimarble;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Test1 {
	@Test
	void map() {
		Map<Integer, Integer> map = new HashMap<>();
		Integer a = map.get(1);
		System.out.println(a);
	}

}
