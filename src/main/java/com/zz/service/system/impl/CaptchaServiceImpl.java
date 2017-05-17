package com.zz.service.system.impl;


import com.zz.service.system.CaptchaService;
import com.zz.util.shengyuan.Setting.CaptchaType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;


/**
 * 验证码服务层实现
 * @Date 2014-01-03
 * @author 欧志辉
 * @version 1.0
 */
@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

	 
	public BufferedImage buildImage(String captchaId) {
		return  null;
	}

	public boolean isValid(CaptchaType captchaType, String captchaId, String captcha) {
		//TODO captchaTypes从shopxx.xml读取
		CaptchaType[] captchaTypes = new CaptchaType[] {CaptchaType.memberLogin,CaptchaType.memberRegister,
				CaptchaType.adminLogin,CaptchaType.review,CaptchaType.consultation,CaptchaType.findPassword,CaptchaType.resetPassword,CaptchaType.other};
		if (captchaType == null || ArrayUtils.contains(captchaTypes, captchaType)) {
			if (StringUtils.isNotEmpty(captchaId) && StringUtils.isNotEmpty(captcha)) {
				try {
					return true;
				} catch (Exception e) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

}