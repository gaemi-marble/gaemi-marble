package codesquad.gaemimarble.config;

import codesquad.gaemimarble.util.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
            .exposedHeaders(Constants.REFRESH_TOKEN)
            .exposedHeaders(HttpHeaders.AUTHORIZATION)
			.allowCredentials(true);
	}
}
