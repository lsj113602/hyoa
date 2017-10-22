package cn.hnhy.hyoa.admin.identity.service;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.admin.identity.entity.Module;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.core.common.web.PageModel;

/**
 * 权限管理业务处理接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月27日 下午6:51:09
 */
public interface IdentityService {
	
	/** 定义CODE位数的常量 */
	int CODE_LEN = 4;
	/**
	 * 登录方法
	 * @param userId 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @param key 是否记住密码
	 * @return Map集合
	 */
	Map<String, Object> login(String userId, String password, String vcode,int key);
	/**
	 * 根据用户id查询所有的角色，再根据角色查询所有的权限
	 * @param userId 用户id
	 * @return Map
	 */
	Map<String, List<String>> getUserAllPopedoms(String userId);
	/**
	 * 根据用户id查询用户
	 * @param userId 主键
	 * @return 用户
	 */
	User getUser(String userId, boolean isMD5);
	/**
	 * 多条件分页查询查询用户
	 * @param user 查询条件
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPage(User user, PageModel pageModel);
	/**
	 * 加载部门
	 * @return List集合
	 */
	List<Map<String, Object>> loadDept();
	/**
	 * 加载职位
	 * @return List集合
	 */
	List<Map<String, Object>> loadJob();
	/**
	 * 查询用户姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	List<String> loadName(String name);
	/**
	 * 加载部门与职位
	 * @return Map集合
	 */
	Map<String, List<Map<String, Object>>> loadDeptAndJob();
	/**
	 * 验证用户名是否重复
	 * @param userId
	 * @return true: 重复  false:不重复
	 */
	boolean validUserId(String userId);
	/**
	 * 添加用户
	 * @param user 用户实体
	 */
	void saveUser(User user);
	/**
	 * 修改用户
	 * @param user 用户实体
	 */
	void updateUser(User user);
	/**
	 * 批量删除用户
	 * @param userIds
	 */
	void deleteUser(String[] userIds);
	/**
	 * 批量审核用户
	 * @param userIds 审核的用户id
	 * @param status 状态码
	 */
	void checkUser(String[] userIds, Short status);
	/**
	 * 分页查询角色
	 * @param pageModel
	 * @return 角色集合
	 */
	List<Role> getRoleByPage(PageModel pageModel);
	/**
	 * 添加角色
	 * @param role
	 */
	void saveRole(Role role);
	/**
	 * 根据主键id获取角色
	 * @param id 主键
	 * @return 角色
	 */
	Role getRole(Long id);
	/**
	 * 修改角色
	 * @param role
	 */
	void updateRole(Role role);
	/**
	 * 删除角色
	 * @param ids
	 */
	void deleteRole(String[] ids);
	/**
	 * 加载操作树
	 * @return List
	 */
	List<Map<String, Object>> loadModuleTree();
	/**
	 * 分页查询操作
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @return 操作集合
	 */
	List<Module> getModuleByPage(String parentCode, PageModel pageModel);
	/**
	 * 添加操作
	 * @param module 操作实体
	 */
	void saveModule(Module module);
	/**
	 * 根据主键code查询操作
	 * @param code
	 * @return 操作实体
	 */
	Module getModule(String code);
	/**
	 * 修改操作
	 * @param module 操作实体
	 */
	void updateModule(Module module);
	/**
	 * 批量删除操作
	 * @param codes
	 */
	void deleteModule(String[] codes);
	/**
	 * 根据角色id分页查询用户(把这个角色中的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPageAndRoleId(Long id, PageModel pageModel);
	/**
	 * 根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	List<User> getUserByPageNotRoleId(Long id, PageModel pageModel);
	/**
	 * 角色绑定用户
	 * @param id 角色id
	 * @param userIds 用户id
	 */
	void bindUser(Long id, String[] userIds);
	/**
	 * 角色解除用户
	 * @param id 角色id
	 * @param userIds 用户id
	 */
	void unBindUser(Long id, String[] userIds);
	/**
	 *  异步加载权限树
	 * @return List
	 */
	List<Map<String, Object>> loadPopedomTree();
	/**
	 * 查询权限(12位操作)
	 * @param moduleCode 8位code
	 * @return List (Module中的code是12位)
	 */
	List<Module> getModulesByCode8(String moduleCode);
	/**
	 * 角色绑定操作
	 * @param role 角色
	 * @param moduleCode 八位的code
	 * @param codes 12位的code
	 */
	void bindModule(Role role, String moduleCode, String codes);
	/**
	 * 查询该角色在这个模块中已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 * @return 操作的集合
	 */
	List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode);
	/**
	 * 根据当前登录用户生成功能菜单树
	 * @return List
	 */
	List<Map<String, Object>> loadMenuTree();
	/**
	 * 找回密码
	 * @param userId
	 * @param question
	 * @param answer
	 * @return
	 */
	String findPwd(String userId, int question, String answer);
	/**
	 * 修改密码
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @param okpwd 确认密码
	 * @return true成功  false失败
	 */
	boolean updatePwd(String oldpwd, String newpwd, String okpwd);
}