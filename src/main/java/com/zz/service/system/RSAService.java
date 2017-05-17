package com.zz.service.system;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA安全认证接口
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
public interface RSAService {

	/**
	 * 生成密钥(添加私钥至Session并返回公钥)
	 * @param request httpServletRequest
	 * @return 公钥
	 */
	public RSAPublicKey generateKey(HttpServletRequest request);

	/**
	 * 移除私钥
	 * @param request httpServletRequest
	 */
	public void removePrivateKey(HttpServletRequest request);

	/**
	 * 解密参数
	 * @param name 参数名称
	 * @param request httpServletRequest
	 * @return 解密内容
	 */
	public String decryptParameter(String name, HttpServletRequest request);

}