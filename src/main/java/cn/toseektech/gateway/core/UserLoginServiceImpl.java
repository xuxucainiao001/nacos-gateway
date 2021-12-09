package cn.toseektech.gateway.core;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.toseektech.gateway.remote.LoginRemote;
import cn.toseektech.login.core.UserLoginService;
import cn.toseektech.login.dto.LoginUser;



@Service
public class UserLoginServiceImpl implements UserLoginService{
	
	@Resource
	private  LoginRemote loginRemote;

	@Override
	public LoginUser getUserFromFirstLogin(String redirectCode) {
		return loginRemote.getUserFromFirstLogin(redirectCode);
	}

	@Override
	public LoginUser getUserIfLogin(Long userId) {
		return loginRemote.getUserIfLogin(userId);
	}

	@Override
	public void loginOut(LoginUser loginUser) {
		loginRemote.loginOut(loginUser);		
	}

}
