package com.zz.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 登陆令牌
 * @Date 2014-01-03
 * @author 欧志辉
 * @version 1.0
 */
public class AuthenticationToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 5898441540965086534L;

	private String captchaCode; 

	/** 验证码 */
	private String captcha;

	/**
	 * @param username 用户名
	 * @param password 密码
	 * @param captchaCode 验证码ID
	 * @param captcha 验证码
	 * @param rememberMe 记住我
	 * @param host IP
	 */
	public AuthenticationToken(String username, String password, String captchaCode, 
			String captcha, boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
		this.captchaCode = captchaCode;
		this.captcha = captcha;
	}
	
	public String getCaptchaCode() {
		return captchaCode;
	}
	
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	
	/**
	 * 获取验证码
	 * @return 验证码
	 */
	public String getCaptcha() {
		return captcha;
	}

	/**
	 * 设置验证码
	 * @param captcha 验证码
	 */
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}