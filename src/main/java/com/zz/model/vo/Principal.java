package com.zz.model.vo;

import java.io.Serializable;

/**
 * 身份信息
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 用户名 */
	private String username;

	/** 真实姓名 */
	private String name;
	
	/**用户类型*/
	private int memberType;

	public Principal(Long id, String username,int memberType) {
		this.id = id;
		this.username = username;
		this.memberType = memberType;
	}
	
	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	/**
	 * 
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 * @param name
	 *            真实姓名
	 */
	public Principal(Long id, String username, String name) {
		this.id = id;
		this.username = username;
		this.name = name;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取真实姓名
	 * 
	 * @return 真实姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置真实姓名
	 * 
	 * @param name
	 *            真实姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	@Override
	public String toString() {
		return username;
	}
}