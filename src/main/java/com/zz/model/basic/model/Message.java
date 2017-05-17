package com.zz.model.basic.model;

import com.zz.util.shengyuan.ResourceUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息
 */
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = 7289310002935043203L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}

	/** 类型 */
	private Type type;

	/** 内容 */
	private String content;

	/** 结果 */
	private Map<String, Object> resultMap = new HashMap<String, Object>();

	/**
	 * 初始化一个新创建的 Message 对象，使其表示一个空消息。
	 */
	public Message() {

	}

	/**
	 * 初始化一个新创建的 Message 对象
	 * 
	 * @param type
	 *            类型
	 * @param content
	 *            内容
	 */
	public Message(Type type, String content) {
		this.type = type;
		this.content = ResourceUtils.getString("message", content);
	}

	/**
	 * @param type
	 *            类型
	 * @param content
	 *            内容(支持{0}{1}{...}的占位符)
	 * @param args
	 *            参数(填充到内容占位符)
	 */
	public Message(Type type, String content, Object... args) {
		this.type = type;
		this.content = ResourceUtils.getString("message", content, args);
	}

	/**
	 * 返回成功消息
	 * 
	 * @param content
	 *            内容(支持{0}{1}{...}的占位符)
	 * @param args
	 *            参数(填充到内容占位符)
	 * @return 成功消息
	 */
	public static Message success(String content, Object... args) {
		return new Message(Type.success, content, args);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content
	 *            内容(支持{0}{1}{...}的占位符)
	 * @param args
	 *            参数(填充到内容占位符)
	 * @return 警告消息
	 */
	public static Message warn(String content, Object... args) {
		return new Message(Type.warn, content, args);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content
	 *            内容(支持{0}{1}{...}的占位符)
	 * @param args
	 *            参数(填充到内容占位符)
	 * @return 错误消息
	 */
	public static Message error(String content, Object... args) {
		return new Message(Type.error, content, args);
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取消息结果
	 * 
	 * @return 结果
	 */
	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	/**
	 * 设置消息结果
	 * 
	 * @param resultMap
	 *            结果
	 */
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	/**
	 * 为Message追加结果
	 * 
	 * @param key
	 * @param val
	 * @return Message
	 */
	public Message addResult(String key, Object val) {
		if (this.resultMap == null) {
			this.resultMap = new HashMap<String, Object>();
		}
		this.resultMap.put(key, val);
		return this;
	}

	/**
	 * 为Message追加结果集
	 * 
	 * @param resultMap
	 *            补充的结果集
	 * @return Message
	 */
	public Message addResultMap(Map<String, Object> resultMap) {
		if (this.resultMap == null) {
			this.resultMap = new HashMap<String, Object>();
		}
		if(resultMap != null){
			this.resultMap.putAll(resultMap);
		}
		return this;
	}
}