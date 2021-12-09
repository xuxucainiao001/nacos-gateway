package cn.toseektech.gateway.remote.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.toseektech.gateway.remote.LoginRemote;
import cn.toseektech.login.dto.LoginUser;

import feign.hystrix.FallbackFactory;

/**
 * 熔断处理
 * @author xuxu
 *
 */
@Component
public class LoginRemoteFallbackFactory implements FallbackFactory<LoginRemote>{


	@Override
	public LoginRemote create(Throwable cause) {
		
		return new LoginRemoteteFallback(cause);
	}

}

/**
 * 熔断实现
 * @author xuxu
 *
 */
class LoginRemoteteFallback implements LoginRemote{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Throwable cause;
	
	public LoginRemoteteFallback(Throwable cause) {
		this.cause=cause;
	}


	@Override
	public LoginUser getUserFromFirstLogin(String redirectCode) {
		logger.error("cn.toseektech.gateway.remote.LoginRemote.getUserFromFirstLogin调用发生异常：",cause);
		return null;
	}


	@Override
	public LoginUser getUserIfLogin(Long userId) {
		logger.error("cn.toseektech.gateway.remote.LoginRemote.checkUserIsLogin 调用发生异常：",cause);
		return null;
	}


	@Override
	public void loginOut(LoginUser loginUser) {
		logger.error("cn.toseektech.gateway.remote.LoginRemote.loginOut 调用发生异常：",cause);		
	}
	
}

