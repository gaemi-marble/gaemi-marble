package codesquad.gaemimarble.Jwt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import codesquad.gaemimarble.Jwt.entity.Jwt;
import codesquad.gaemimarble.util.Constants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtService {
	private final JwtProvider jwtProvider;

	private final Map<String, String> tokenMap = new HashMap<>();

	public Jwt reissueAccessToken(String refreshToken, String playerId) {
		if (!tokenMap.containsKey(playerId) || !tokenMap.get(playerId).equals(refreshToken)) {
			throw new RuntimeException("잘못된 refreshToken입니다");
		}
		tokenMap.remove(playerId);
		Jwt jwt = jwtProvider.createJwt(Map.of(Constants.PLAYER_ID, playerId));
		tokenMap.put(playerId, refreshToken);
		return jwt;
	}

	public void logout(String accessToken, String playerId) {
		tokenMap.put(accessToken, accessToken);
		tokenMap.remove(playerId);
	}

	public Map<String, String> getTokenMap() {
		return tokenMap;
	}
}
