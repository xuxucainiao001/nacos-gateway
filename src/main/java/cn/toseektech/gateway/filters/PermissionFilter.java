package cn.toseektech.gateway.filters;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cn.hutool.json.JSONUtil;
import cn.toseektech.common.enums.ResponseEnum;
import cn.toseektech.common.model.ResponseDto;
import cn.toseektech.common.utils.IpUtil;
import cn.toseektech.login.core.LoginProcessEngine;
import reactor.core.publisher.Mono;

@Component
public class PermissionFilter implements GlobalFilter, Ordered{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private LoginProcessEngine loginProcessEngine;


	@Override
	public int getOrder() {
		return -1;
	}

	/**
	 * 验证登录 ，且封装登录信息
	 */

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String requestPath = request.getPath().toString();
		logger.info("请求来源地址：{}, 请求路径：{}", IpUtil.getIpAddr0(request), requestPath);
		// 验证是否需要登录逻辑	
		if (!loginProcessEngine.process(request, response)) {
			ResponseDto<Object> responseDto = new ResponseDto<>().code(ResponseEnum.LOGIN_ERROR.getCode())
					.message(ResponseEnum.LOGIN_ERROR.getMessage());
			// 如果验证失败无需转发给服务器
			response.setStatusCode(HttpStatus.OK);
			return response.writeAndFlushWith(Mono.just(Mono.just(response.bufferFactory().wrap(JSONUtil.toJsonPrettyStr(responseDto).getBytes()))));			
		}
		return chain.filter(exchange);
	}

}


