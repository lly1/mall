package com.mall.shiro;

import com.mall.api.constant.ErrorType;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
public class WebToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;

	/**
	 * 登录来源，新增字段，方便区分登录来源，比如PC端的账号不能登录out端
	 * yuerfeng 2018-10-12
	 */
	private String loginSrc;
	/**
	* 登录失败错误码
	*/
	private ErrorType failCode;
	
	public WebToken() {
		super();
	}

	public WebToken(String username, char[] password,
					boolean rememberMe, String host, String captcha, String loginSrc) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.loginSrc = loginSrc;
	}
	
}