package com.zz.service.system;



import com.zz.util.shengyuan.Setting;

import java.awt.image.BufferedImage;


/**
 * 验证码服务层接口
 * @Date 2014-01-03
 * @author 欧志辉
 * @version 1.0
 */
public interface CaptchaService {
	
	/**
	 * 生成验证码图片
	 * @param captchaId 验证ID
	 * @return BufferedImage 验证码图片
	 */
	public BufferedImage buildImage(String captchaId);

	/**
	 * 验证码验证
	 * @param captchaType 验证码类型
	 * @param captchaId 验证ID
	 * @param captcha 验证码(忽略大小写)
	 * @return boolean 验证码验证是否通过
	 */
	public boolean isValid(Setting.CaptchaType captchaType, String captchaId, String captcha);

}