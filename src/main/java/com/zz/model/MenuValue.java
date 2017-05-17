package com.zz.model;



import com.zz.model.basic.dao.annotation.Entity;
import com.zz.model.basic.dao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单权限值实体
 * @Date 2014-12-29
 * @author 欧志辉
 * @version 1.0
 */
@Entity(name = "xx_menu_value")
public class MenuValue implements Serializable {

	private static final long serialVersionUID = 3959580480444072004L;
	
	@Id
	private Long id;

	/** 创建日期 */
	private Date createDate;

	/** 修改日期 */
	private Date modifyDate;
	
	/** 菜单权限值 */
	private String vName;

	/**
	 * 获取ID
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取创建日期
	 * @return 创建日期
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 设置创建日期
	 * @param createDate 创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取修改日期
	 * @return 修改日期
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * 设置修改日期
	 * @param modifyDate 修改日期
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	/**
	 * 获取菜单权限值
	 * @return 菜单权限值
	 */
	public String getvName() {
		return vName;
	}
	
	/**
	 * 设置菜单权限值
	 * 
	 * @param vName 菜单权限值
	 */
	public void setvName(String vName) {
		this.vName = vName;
	}


}