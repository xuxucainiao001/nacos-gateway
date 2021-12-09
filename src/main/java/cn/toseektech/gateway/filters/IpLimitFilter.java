package cn.toseektech.gateway.filters;

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
import cn.toseektech.common.limit.IPLimiter;
import cn.toseektech.common.model.ResponseDto;
import cn.toseektech.common.utils.IpUtil;
import reactor.core.publisher.Mono;

/**
 * ip限流
 * @author xuxu
 *
 */
@Component
public class IpLimitFilter implements GlobalFilter, Ordered {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public int getOrder() {
		return -400;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		String ip = IpUtil.getIpAddr0(request);
		if (!IPLimiter.dolimit(ip)) {
			// 限流失败拦截,不再路由
			logger.info("该用户ip访问流量超过限制,ip:{}", ip);
			response.setStatusCode(HttpStatus.OK);
			ResponseDto<Object> responseDto = new ResponseDto<>().code(ResponseEnum.IP_LIMIT_ERROR.getCode())
					.message(ResponseEnum.IP_LIMIT_ERROR.getMessage());
			return response.writeAndFlushWith(Mono.just(Mono.just(response.bufferFactory().wrap(JSONUtil.toJsonPrettyStr(responseDto).getBytes()))));
		} else {
			return chain.filter(exchange);
		}
	}

}
