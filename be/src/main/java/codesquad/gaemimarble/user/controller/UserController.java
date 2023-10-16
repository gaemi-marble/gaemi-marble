package codesquad.gaemimarble.user.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquad.gaemimarble.Jwt.dto.request.JwtRefreshTokenRequest;
import codesquad.gaemimarble.Jwt.entity.Jwt;
import codesquad.gaemimarble.Jwt.service.JwtService;
import codesquad.gaemimarble.user.dto.request.UserLoginRequest;
import codesquad.gaemimarble.user.dto.request.UserSignUpRequest;
import codesquad.gaemimarble.user.service.UserService;
import codesquad.gaemimarble.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	private final JwtService jwtService;

	@PostMapping("/api/signup")
	public ResponseEntity<Void> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
		userService.signUp(userSignUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/api/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequest userLoginRequest,
		HttpServletResponse response) {
		Jwt jwt = userService.login(userLoginRequest);
		response.setHeader(HttpHeaders.AUTHORIZATION, jwt.getAccessToken());
		response.setHeader(Constants.REFRESH_TOKEN, jwt.getRefreshToken());
		return ResponseEntity.ok(Map.of(Constants.PLAYER_ID, userLoginRequest.getPlayerId()));
	}

	@GetMapping("/reissue-access-token")
	public Jwt reissueToken(@RequestBody JwtRefreshTokenRequest jwtRefreshTokenRequest, HttpServletRequest request) {
		return jwtService.reissueAccessToken(
			jwtRefreshTokenRequest.getRefreshToken(), (String)request.getAttribute(Constants.PLAYER_ID));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(
			Constants.TOKEN_PREFIX.length()).replace("\"", "");
		jwtService.logout(accessToken, (String)request.getAttribute(Constants.PLAYER_ID));
		return ResponseEntity.ok().build();
	}
}
