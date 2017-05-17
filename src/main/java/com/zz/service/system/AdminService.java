package com.zz.service.system;



import com.zz.model.Admin;
import com.zz.model.basic.model.Page;
import com.zz.model.basic.service.BaseService;
import com.zz.util.shengyuan.Pageable;


import java.util.List;


public interface AdminService extends BaseService<Admin, Long> {

	/**
	 * 判断用户名是否存在
	 * @param username 用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	public boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * @param username 用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	public Admin findByUsername(String username);

	/**
	 * 根据ID查找权限
	 * @param id ID
	 * @return 权限,若不存在则返回null
	 */
	public List<String> findAuthorities(Long id);

	/**
	 * 判断管理员是否登录
	 * @return 管理员是否登录
	 */
	public boolean isAuthenticated();

	/**
	 * 获取当前登录管理员
	 * @return 当前登录管理员,若不存在则返回null
	 */
	public Admin getCurrent();

	/**
	 * 获取当前登录用户名
	 * @return 当前登录用户名,若不存在则返回null
	 */
	public String getCurrentUsername();
	
	/**
	 * 查找客服所有人员管理员
	 * @return List<Admin>
	 */
	public List<Admin> findCustomServiceAdmins();
	
	/**
	 * 新增后台管理员
	 * @param admin 后台管理员实体
	 * @param roleIds 角色主键数组
	 * @return Long 保存实体的主键
	 */
	public Long saveAdmin(Admin admin, Long[] roleIds);
	
	/**
	 * 新增后台管理员
	 * @param admin 后台管理员实体
	 * @param roleIds 角色主键数组
	 */
	public void updateAdmin(Admin admin, Long[] roleIds);
	
	/**
	 * 批量删除管理员
	 * @param admins
	 */
	public void batchDelete(List<Admin> admins);
	
	/**
	 * 分页查找所有的后台管理员
	 * @param pageable
	 * @return Page<Admin>
	 */
	public Page<Admin> findPage(Pageable pageable);
}