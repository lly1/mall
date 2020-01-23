
package com.mall.common;

import com.mall.constant.ErrorType;
import lombok.Data;

@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;

	/**
	 * 登录来源，新增字段，方便区分登录来源，比如PC端的账号不能登录out端
	 * lly
	 */
	private String loginSrc;
	/**
	* 登录失败错误码
	*/
	private ErrorType failCode;
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
                                 boolean rememberMe, String host, String captcha, String loginSrc) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.loginSrc = loginSrc;
	}
	
}