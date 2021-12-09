package cn.toseektech.gateway.core;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import cn.hutool.json.JSONUtil;
import cn.toseektech.common.enums.ResponseEnum;
import cn.toseektech.common.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理
 * @author xuxu
 *
 */

@Slf4j
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.OK);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		log.error("gateway网关调用发生异常：", ex);
		if (ex instanceof BlockException) {
			ResponseDto<Object> responseDto = new ResponseDto<>().code(ResponseEnum.LIMIT_ERROR.getCode())
					.message(ResponseEnum.LIMIT_ERROR.getMessage());
			return response.writeAndFlushWith(Mono.just(Mono.just(response.bufferFactory().wrap(JSONUtil.toJsonPrettyStr(responseDto).getBytes()))));
		} else {
			ResponseDto<Object> responseDto = new ResponseDto<>().code(ResponseEnum.SYSTEM_ERROR.getCode())
					.message(ResponseEnum.SYSTEM_ERROR.getMessage());
			return response.writeAndFlushWith(Mono.just(Mono.just(response.bufferFactory().wrap(JSONUtil.toJsonPrettyStr(responseDto).getBytes()))));
		}
	}

}
