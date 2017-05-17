package com.zz.config.shiro;


import com.zz.model.Admin;
import com.zz.service.system.AdminService;
import com.zz.service.system.RSAService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;


import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 权限认证
 * @Date 2014-12-31
 * @author 欧志辉
 * @version 1.0
 */
public class AuthenticationFilter extends FormAuthenticationFilter {

	/** 默认加密密码参数名称 */
	private static final String DEFAULT_EN_PASSWORD_PARAM = "enPassword";

	/** 默认验证ID参数名称 */
	private static final String DEFAULT_CAPTCHA_ID_PARAM = "captchaId";

	/** 默认验证码参数名称 */
	private static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	
	/** 当前用户门店编号 参数名称 */
	private static final String DEFAULT_MID_PARAM = "adminMid";

	/** 加密密码参数名称 */
	private String enPasswordParam = DEFAULT_EN_PASSWORD_PARAM;

	/** 验证ID参数名称 */
	private String captchaIdParam = DEFAULT_CAPTCHA_ID_PARAM;

	/** 验证码参数名称 */
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
		String username = getUsername(servletRequest);
		String password = getPassword(servletRequest);
		String captcha = getCaptcha(servletRequest);
		String captchaCode = getCaptchaCode(servletRequest);
		boolean rememberMe = isRememberMe(servletRequest);
		String host = getHost(servletRequest);
		return new AuthenticationToken(username, password, captchaCode,captcha, rememberMe, host);
	}

	private String getCaptchaCode(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		return (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		 
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject, ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		Session session = subject.getSession();
		Map<Object, Object> attributes = new HashMap<Object, Object>();
		Collection<Object> keys = session.getAttributeKeys();
		for (Object key : keys) {
			attributes.put(key, session.getAttribute(key));
		}
		session.stop();
		session = subject.getSession();
		Admin admin = adminService.findByUsername(subject.getPrincipal().toString());
		session.setAttribute(DEFAULT_MID_PARAM, admin.getId());
		for (Entry<Object, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
	}

	@Override
	protected String getPassword(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String password = rsaService.decryptParameter(enPasswordParam, request);
		rsaService.removePrivateKey(request);
		return password;
	}

	 

	/**
	 * 获取验证
	 * @param servletRequest ServletRequest
	 * @return 验证码
	 */
	protected String getCaptcha(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, captchaParam);
	}

	/**
	 * 获取"加密密码"参数名称
	 * @return "加密密码"参数名称
	 */
	public String getEnPasswordParam() {
		return enPasswordParam;
	}

	/**
	 * 设置"加密密码"参数名称
	 * @param enPasswordParam "加密密码"参数名称
	 */
	public void setEnPasswordParam(String enPasswordParam) {
		this.enPasswordParam = enPasswordParam;
	}

	/**
	 * 获取"验证ID"参数名称
	 * @return "验证ID"参数名称
	 */
	public String getCaptchaIdParam() {
		return captchaIdParam;
	}

	/**
	 * 设置"验证ID"参数名称
	 * @param captchaIdParam "验证ID"参数名称
	 */
	public void setCaptchaIdParam(String captchaIdParam) {
		this.captchaIdParam = captchaIdParam;
	}

	/**
	 * 获取"验证码"参数名称
	 * @return "验证码"参数名称
	 */
	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 设置"验证码"参数名称
	 * @param captchaParam "验证码"参数名称
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

}