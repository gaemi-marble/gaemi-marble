package codesquad.gaemimarble.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import codesquad.gaemimarble.Jwt.service.JwtProvider;
import codesquad.gaemimarble.Jwt.service.JwtService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {
	private final JwtProvider jwtProvider;
	private final JwtService jwtService;

	@Bean
	public FilterRegistrationBean<Filter> jwtAuthorizationFilter(ObjectMapper mapper) {
		FilterRegistrationBean<Filter> filterRegistrationBean = new
			FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new AuthorizationFilter(jwtProvider, jwtService));
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
