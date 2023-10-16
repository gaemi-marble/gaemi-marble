package codesquad.gaemimarble.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.cors.CorsUtils;

import codesquad.gaemimarble.Jwt.service.JwtProvider;
import codesquad.gaemimarble.Jwt.service.JwtService;
import codesquad.gaemimarble.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter implements Filter {
	private static final String[] whiteListUris = {"/api/signup", "/api/login", "/api/reissue-token",
		"/api/ws/**", "/api/ws**"};

	private final JwtProvider jwtProvider;
	private final JwtService jwtService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		if (CorsUtils.isPreFlightRequest(httpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		if (whiteListCheck(httpServletRequest.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}

		if (!isContainToken(httpServletRequest)) {
			throw new MalformedJwtException("토큰이 없습니다" + httpServletRequest.getRequestURI());
		}

		if (isBlackListed(httpServletRequest)) {
			throw new MalformedJwtException("로그아웃된 토큰입니다" + httpServletRequest.getRequestURI());
		}

		try {
			String token = getToken(httpServletRequest);
			Claims claims = jwtProvider.getClaims(token);
			request.setAttribute(Constants.PLAYER_ID, claims.get(Constants.PLAYER_ID));
			chain.doFilter(request, response);
		} catch (RuntimeException e) {
			throw new MalformedJwtException("올바르지 않은 토큰입니다." + httpServletRequest.getRequestURI());
		}
	}

	private boolean isBlackListed(HttpServletRequest httpServletRequest) {
		String accessToken = getToken(httpServletRequest);
		return jwtService.getTokenMap().containsKey(accessToken);
	}

	private boolean whiteListCheck(String uri) {
		return PatternMatchUtils.simpleMatch(whiteListUris, uri);
	}

	private boolean isContainToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization != null && authorization.startsWith(Constants.TOKEN_PREFIX);
	}

	private String getToken(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization.substring(Constants.TOKEN_PREFIX.length()).replace("\"", "");
	}
}
