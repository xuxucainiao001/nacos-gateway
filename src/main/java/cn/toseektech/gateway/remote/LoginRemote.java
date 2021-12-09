package cn.toseektech.gateway.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.toseektech.gateway.remote.fallback.LoginRemoteFallbackFactory;
import cn.toseektech.login.dto.LoginUser;



@FeignClient(name = "sso-login",path = "/user",fallbackFactory = LoginRemoteFallbackFactory.class)
public interface LoginRemote {
	
	@GetMapping("/getUserFromFirstLogin/{code}")
	public LoginUser getUserFromFirstLogin(@PathVariable("code") String redirectCode);

	@GetMapping("/getUserIfLogin/{userId}")
	public LoginUser getUserIfLogin(@PathVariable("userId") Long userId);

	@PostMapping("/loginOut")
	public void loginOut(@RequestBody LoginUser loginUser);
     
}
