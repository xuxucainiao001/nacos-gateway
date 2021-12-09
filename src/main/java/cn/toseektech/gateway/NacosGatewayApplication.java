package cn.toseektech.gateway;


import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import cn.toseektech.login.annotations.EnableToSeekTechLogin;

@EnableToSeekTechLogin
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"cn.toseektech.gateway.remote.**"})
public class NacosGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosGatewayApplication.class, args);
	}
	
	/**
	 * 防止feign调用 json转换异常
	 * @param converters
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
	    return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
	}

}
