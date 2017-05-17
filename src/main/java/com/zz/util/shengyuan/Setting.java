package com.zz.util.shengyuan;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class Setting {

	private static final String CONF_FILE_NAME = "setting";

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 会员登录 */
		memberLogin,

		/** 会员注册 */
		memberRegister,

		/** 后台登录 */
		adminLogin,

		/** 商品评论 */
		review,

		/** 商品咨询 */
		consultation,

		/** 找回密码 */
		findPassword,

		/** 重置密码 */
		resetPassword,

		/** 其它 */
		other
	}

	/** 小数位精确方式 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}
	/***
	 * 订单状态改为完成
	 * @return
	 */
	public static int getNeedsToCompleteOrderDate(){
		return ResourceUtils.getInt(CONF_FILE_NAME, "needsToCompleteOrderDate", 7);
	}
	
	
	/***
	 * 订单状态改为完成
	 * @return
	 */
	public static int getNeedsToCompleteMerchantOrderDate(){
		return ResourceUtils.getInt(CONF_FILE_NAME, "needsToCompleteMerchantOrderDate", 15);
	}
	
	

	/** 连续登录失败最大次数 */
	public static int getAccountLockCount() {
		return ResourceUtils.getInt(CONF_FILE_NAME, "accountLockCount", 5);
	}

	/** 自动解锁时间 */
	public static int getAccountLockTime() {
		return ResourceUtils.getInt(CONF_FILE_NAME, "accountLockTime", 10);
	}

	/** 货币符号 */
	public static String getCurrencySign() {
		return ResourceUtils.getString(CONF_FILE_NAME, "currencySign", "￥");
	}

	/** 货币单位 */
	public static String getCurrencyUnit() {
		return ResourceUtils.getString(CONF_FILE_NAME, "currencyUnit", "元");
	}

	/** 价格精确位数 */
	public static Integer getPriceScale() {
		return ResourceUtils.getInt(CONF_FILE_NAME, "priceScale", 2);
	}

	/***
	 * 满多少免邮
	 * 
	 * @return
	 */
	public static double getProductCountPrice() {
		return ResourceUtils.getInt(CONF_FILE_NAME, "priceScale", 38);
	}

	/***
	 * 获取运费
	 * 
	 * @return
	 */
	public static String getTransportationCosts() {
		return ResourceUtils.getString(CONF_FILE_NAME, "transportationCosts", 8);
	}

	/***
	 * 获取禁用用户名
	 * 
	 * @return
	 */
	public static String[] getDisabledUsernames() {
		return StringUtils.split("ResourceUtils.getString(CONF_FILE_NAME, 'disabledUsername', 'admin,管理员')", ",");
	}

	/***
	 * 获取20元优惠劵
	 * 
	 * @return
	 */
	public static Long getTwenty() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "twenty", 9);
	}

	/***
	 * 获取10元优惠劵
	 * 
	 * @return
	 */
	public static Long getTen() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "ten", 2);
	}

	/** 注册送优惠卷 */
	public static Boolean getRegeditGiveIsEnabled() {
		return ResourceUtils.getBoolean(CONF_FILE_NAME, "regeditGiveIsEnabled", true);
	}

	/**
	 * 获取默认市场价换算比例
	 * 
	 * @return 默认市场价换算比例
	 */
	public static String getDefaultMarketPriceScale() {
		return ResourceUtils.getString(CONF_FILE_NAME, "defaultMarketPriceScale", "1.2");
	}

	/**
	 * 获取默认闪豆换算比例
	 * 
	 * @return 默认闪豆换算比例
	 */
	public static String getDefaultPointScale() {
		return ResourceUtils.getString(CONF_FILE_NAME, "defaultPointScale", "1");
	}

	/** 用户名最小长度 */
	public static Long getUsernameMinLength() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "usernameMinLength", 6);
	}

	/** 用户名最大长度 */
	public static Long getUsernameMaxLength() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "usernameMaxLength", 20);
	}

	/** 密码最小长度 */
	public static Long getPasswordMinLength() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "passwordMinLength", 6);
	}

	/** 密码最大长度 */
	public static Long getPasswordMaxLength() {
		return ResourceUtils.getLong(CONF_FILE_NAME, "passwordMaxLength", 20);
	}

	/**
	 * 获取是否允许E-mail重复注册
	 */
	public static Boolean getIsDuplicateEmail() {
		return ResourceUtils.getBoolean(CONF_FILE_NAME, "isDuplicateEmail", false);
	}
	/**
	 * 获取财务收款金额需复核的收款金额，默认800
	 */
	public static BigDecimal getFinanceVerifySum() {
		return ResourceUtils.getBigDecimal(CONF_FILE_NAME, "financeVerifySum", new BigDecimal(800));
	}

	public static String getSendAddres() {
		return ResourceUtils.getString(CONF_FILE_NAME, "sendAddres");
	}

	public static String getSendPeople() {
		return ResourceUtils.getString(CONF_FILE_NAME, "sendPeople");
	}

	public static String getSendUnitName() {
		return ResourceUtils.getString(CONF_FILE_NAME, "sendUnitName");
	}

	public static String getSendPhone() {
		return ResourceUtils.getString(CONF_FILE_NAME, "sendPhone");
	}
	
}
