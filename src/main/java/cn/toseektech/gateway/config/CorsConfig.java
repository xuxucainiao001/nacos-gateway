package cn.toseektech.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import cn.toseektech.login.core.LoginConstants;


/**
 * springcloud gateway 跨域解决方案
 * @author xuxu
 *
 */

@Configuration
public class CorsConfig {
	
	    @Bean
	    public CorsWebFilter corsFilter() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.addAllowedMethod("*");
	        config.addAllowedOrigin("*");
	        config.addAllowedHeader("*");
	        config.setMaxAge(1800L);
	        config.setAllowCredentials(true);
	        /*若不设置 Access-Control-Expose-Headers，前端拿不到 User-Login 响应头*/
	        config.addExposedHeader(LoginConstants.TOKEN_NAME);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
	        source.registerCorsConfiguration("/**", config);
	        return new CorsWebFilter(source);
	    }

}
