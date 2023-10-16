package codesquad.gaemimarble.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.gaemimarble.Jwt.entity.Jwt;
import codesquad.gaemimarble.Jwt.service.JwtProvider;
import codesquad.gaemimarble.user.dto.UserMapper;
import codesquad.gaemimarble.user.dto.request.UserLoginRequest;
import codesquad.gaemimarble.user.dto.request.UserSignUpRequest;
import codesquad.gaemimarble.user.entity.User;
import codesquad.gaemimarble.user.repository.UserRepository;
import codesquad.gaemimarble.util.Constants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public void signUp(UserSignUpRequest userSignUpRequest) {
		if (userRepository.existsByPlayerId(userSignUpRequest.getPlayerId())) {
			// TODO: 커스텀 예외 처리 필요
			throw new RuntimeException("이미 존재하는 회원입니다");
		}

		User user = userRepository.save(UserMapper.INSTANCE.toUser(userSignUpRequest));
	}

	@Transactional(readOnly = true)
	public Jwt login(UserLoginRequest userLoginRequest) {
		User user = userRepository.findByPlayerId(userLoginRequest.getPlayerId());
		if (!user.getPassword().equals(userLoginRequest.getPassword())) {
			throw new RuntimeException("비번 잘못 됨");
		}
		return jwtProvider.createJwt(Map.of(Constants.PLAYER_ID, user.getPlayerId()));
	}
}
