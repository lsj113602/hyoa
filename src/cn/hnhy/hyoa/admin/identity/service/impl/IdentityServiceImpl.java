package cn.hnhy.hyoa.admin.identity.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import cn.hnhy.hyoa.admin.AdminConstant;
import cn.hnhy.hyoa.admin.identity.dao.DeptDao;
import cn.hnhy.hyoa.admin.identity.dao.JobDao;
import cn.hnhy.hyoa.admin.identity.dao.ModuleDao;
import cn.hnhy.hyoa.admin.identity.dao.PopedomDao;
import cn.hnhy.hyoa.admin.identity.dao.RoleDao;
import cn.hnhy.hyoa.admin.identity.dao.UserDao;
import cn.hnhy.hyoa.admin.identity.dao.impl.UserDaoImpl;
import cn.hnhy.hyoa.admin.identity.entity.Module;
import cn.hnhy.hyoa.admin.identity.entity.Popedom;
import cn.hnhy.hyoa.admin.identity.entity.Role;
import cn.hnhy.hyoa.admin.identity.entity.User;
import cn.hnhy.hyoa.admin.identity.service.IdentityService;
import cn.hnhy.hyoa.core.action.VerifyAction;
import cn.hnhy.hyoa.core.common.email.EmailSender;
import cn.hnhy.hyoa.core.common.security.MD5;
import cn.hnhy.hyoa.core.common.web.CookieTools;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.GeneratorDao;
import cn.hnhy.hyoa.core.exception.OAException;


/**
 * 权限管理业务处理接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月4日 下午10:19:09
 */
public class IdentityServiceImpl implements IdentityService {
	/** 组合该模块所有的数据访问接口，实现该模块所有的业务处理 */
	@Resource
	private DeptDao deptDao;
	@Autowired
	private JobDao jobDao;
	@Resource
	private ModuleDao moduleDao;
	@Resource
	private PopedomDao popedomDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserDao userDao;
	@Resource
	private GeneratorDao generatorDao;
	@Resource
	private EmailSender emailSender;
	
	
	/** TODO############################# 用户管理业务处理 ################################## */
	/**
	 * 登录方法
	 * @param userId 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @param key 是否记住密码
	 * @return Map集合
	 */
	public Map<String, Object> login(String userId, String password, String vcode,int key){
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("tip", "验证码不正确！");
			map.put("status", 1);
			/** 获取Session中的验证码 */
			String code = (String)ActionContext.getContext()
						.getSession().get(VerifyAction.VERIFY_CODE);
			/** 判断验证码是否正确 */
			if (code != null && code.equalsIgnoreCase(vcode)){
				/**  判断用户名与密码  */
				if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(password)){
					/** 查询用户 */
					User user = getUser(userId, false);
					/** 判断用户与用户密码 */
					if (user != null && user.getPassWord().equals(MD5.getMD5(password))){
						/** 判断用户状态码 */
						if (user.getStatus() == 1){
							/** 存入Session */
							ActionContext.getContext().getSession().put(AdminConstant.SESSION_USER, user);
							/** 判断 key 是否记住用户 */
							if (key == 1){
								/** 记住用户，操作cookie */
								CookieTools.addCookie(AdminConstant.COOKIE_NAME, user.getUserId(), AdminConstant.MAX_AGE);
							}
							map.put("tip", "登录成功！");
							map.put("status", 0);
							
							/** 根据用户id查询所有的角色，再根据角色查询所有的权限 */
							Map<String, List<String>> userAllPopedoms = getUserAllPopedoms(user.getUserId());
							/** 存入Session */
							ActionContext.getContext().getSession().put(AdminConstant.USER_ALL_POPEDOMS, userAllPopedoms);
						}else{
							// 	0新建,1审核,2不通过审核,3冻结 
							String[] arr = {"新建","审核","不通过","冻结"};
							map.put("tip", "您处于【"+ arr[user.getStatus()] +"】状态，请联系管理人员！");
							map.put("status", 4);
						}
					}else{
						map.put("tip", "用户名或密码不正确！");
						map.put("status", 3);
					}
				}else{
					map.put("tip", "用户名或密码不能为空！");
					map.put("status", 2);
				}
			}
			return map;
		}catch(Exception ex){
			throw new OAException("登录方法时出现异常！", ex);
		}
	}
	
	/**
	 * 根据用户id查询所有的角色，再根据角色查询所有的权限
	 * @param userId 用户id
	 * @return Map
	 */
	public Map<String, List<String>> getUserAllPopedoms(String userId){
		try{
			/** 根据用户id查询所有的角色，再根据角色查询所有的权限(12位code) */
			List<String> operaCodeLists = popedomDao.getOperaCodeByUserId(userId);
			// [000100010001, 000100010002, 000100010003, 
			//  000100020001, 000100020002, 000100020003, 000100020004,
			//  000100030001, 000100030002, 000100030003, 000100030004, 000100030005, 
			//  000100040001, 000100040002, 
			//  000100050001, 
			//  000100060001, 
			//  000200010001, 000200010002, 000200010003
			Map<String, List<String>> userPopedomMaps = new HashMap<>();
			// key : 00010001 (八位)
			// value : [000100010001, 000100010002, 000100010003] (12位)
			String key = null;
			List<String> value = new ArrayList<String>();
			for (String operaCode : operaCodeLists){
				if (key != null && !operaCode.startsWith(key)){
					userPopedomMaps.put(key, value);
					value = new ArrayList<String>();
				}
				key = operaCode.substring(0, operaCode.length() - CODE_LEN); // 8位 (00010001)
				value.add(getModule(operaCode).getUrl()); // 12位
			}
			/** 把最后一组添加到userPopedomMaps集合中 */
			if (key != null && value.size() > 0){
				userPopedomMaps.put(key, value);
			}
			
			for (Map.Entry<String, List<String>> entry : userPopedomMaps.entrySet()){
				System.out.println(entry.getKey() + "-->" + entry.getValue());
			}
			
			return userPopedomMaps;
		}catch(Exception ex){
			throw new OAException("根据用户id查询所有的角色，再根据角色查询所有的权限时出现异常！", ex);
		}
	}
	
	/**
	 * 根据用户id查询用户
	 * @param userId 主键
	 * @return 用户
	 */
	public User getUser(String userId, boolean isMD5){
		try{
			User user = null;
			if (isMD5){
				user = userDao.getUser(userId);
			}else{
				user = userDao.get(User.class, userId);
			}
			if (user != null){
				if (user.getDept() != null) user.getDept().getId();
				if (user.getJob() != null) user.getJob().getCode();
			}
			return user;
		}catch(Exception ex){
			throw new OAException("根据用户id查询用户时出现异常！", ex);
		}
	}
	/**
	 * 多条件分页查询查询用户
	 * @param user 查询条件
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPage(User user, PageModel pageModel){
		try{
			List<User> users = userDao.getUserByPage(user, pageModel);
			/** 加载延迟的属性 */
			for (User u : users){
				/** 加载部门 */
				if (u.getDept() != null) u.getDept().getName();
				/** 加载职位 */
				if (u.getJob() != null) u.getJob().getName();
				/** 加载创建人 */
				if (u.getCreater() != null) u.getCreater().getName();
				/** 加载审核人 */
				if (u.getChecker() != null) u.getChecker().getName();
			}
			return users;
		}catch(Exception ex){
			throw new OAException("多条件分页查询查询用户时出现异常！", ex);
		}
	}
	/**
	 * 加载部门
	 * @return List 集合
	 */
	public List<Map<String, Object>> loadDept(){
		try{
			return deptDao.getDeptByIdAndName();
		}catch(Exception ex){
			throw new OAException("加载部门时出现异常！", ex);
		}
	}
	/**
	 * 加载职位
	 * @return List 集合
	 */
	public List<Map<String, Object>> loadJob(){
		try{
			return jobDao.getJobByCodeAndName();
		}catch(Exception ex){
			throw new OAException("加载职位时出现异常！", ex);
		}
	}
	/**
	 * 查询用户姓名
	 * @param name 查询条件
	 * @return List集合
	 */
	public List<String> loadName(String name){
		try{
			return userDao.getUserByName(name);
		}catch(Exception ex){
			throw new OAException("查询用户姓名时出现异常！", ex);
		}
	}
	/**
	 * 加载部门与职位
	 * @return Map集合
	 */
	public Map<String, List<Map<String, Object>>> loadDeptAndJob(){
		try{
			// {"depts" : [{id : 1, name : ''},{id : 1, name : ''}],
			//   "jobs" : [{code : '', name : ''}]}
			Map<String, List<Map<String, Object>>> maps = new HashMap<>();
			maps.put("depts", loadDept());
			maps.put("jobs", loadJob());
			return maps;
		}catch(Exception ex){
			throw new OAException("加载部门与职位时出现异常！", ex);
		}
	}
	/**
	 * 验证用户名是否重复
	 * @param userId
	 * @return true: 重复  false:不重复
	 */
	public boolean validUserId(String userId){
		try{
			return getUser(userId, false) != null;
		}catch(Exception ex){
			throw new OAException("验证用户名是否重复时出现异常！", ex);
		}
	}
	/**
	 * 添加用户
	 * @param user 用户实体
	 */
	public void saveUser(User user){
		try{
			user.setCreateDate(new Date());
			user.setCreater(AdminConstant.getSessionUser());
			user.setPassWord(MD5.getMD5(user.getPassWord()));
			userDao.save(user);
		}catch(Exception ex){
			throw new OAException("添加用户时出现异常！", ex);
		}
	}
	/**
	 * 修改用户
	 * @param user 用户实体
	 */
	public void updateUser(User user){
		try{
			/** 获取持久化状态用户 */
			User u = userDao.get(User.class, user.getUserId());
			u.setAnswer(user.getAnswer());
			u.setDept(user.getDept());
			u.setEmail(user.getEmail());
			u.setJob(user.getJob());
			u.setModifier(AdminConstant.getSessionUser());
			u.setModifyDate(new Date());
			u.setName(user.getName());
			u.setPhone(user.getPhone());
			u.setQqNum(user.getQqNum());
			u.setQuestion(user.getQuestion());
			u.setSex(user.getSex());
			u.setTel(user.getTel());
		}catch(Exception ex){
			throw new OAException("修改用户时出现异常！", ex);
		}
	}
	/**
	 * 批量删除用户
	 * @param userIds
	 */
	public void deleteUser(String[] userIds){
		try{
			/** 第一种方式 */
			/*for (String userId : userIds){
				userDao.delete(getUser(userId, false));
			}*/
			/** 第二种方式 */
			/*for (String userId : userIds){
				User u = new User();
				u.setUserId(userId);
				userDao.delete(u);
			}*/
			/** 第三种方式 */
			userDao.deleteUser(userIds);
		}catch(Exception ex){
			throw new OAException("批量删除用户时出现异常！", ex);
		}
	}
	/**
	 * 批量审核用户
	 * @param userIds 审核的用户id
	 * @param status 状态码
	 */
	public void checkUser(String[] userIds, Short status){
		try{
			/** 第一种方式 */
			/*for (String userId : userIds){
				User u = getUser(userId, false);
				u.setXxxx();
			}*/
			
			/** 第二种方式 */
			userDao.checkUser(userIds, status);
		}catch(Exception ex){
			throw new OAException("批量删除用户时出现异常！", ex);
		}
	}
	
	/** TODO############################# 角色管理业务处理 ################################## */
	/**
	 * 分页查询角色
	 * @param pageModel
	 * @return 角色集合
	 */
	public List<Role> getRoleByPage(PageModel pageModel){
		try{
			List<Role> roles = roleDao.getRoleByPage(pageModel);
			/** 延迟的属性 */
			for (Role role : roles){
				if (role.getCreater() != null) role.getCreater().getName();
				if (role.getModifier() != null) role.getModifier().getName();
			}
			return roles;
		}catch(Exception ex){
			throw new OAException("分页查询角色时出现异常！", ex);
		}
	}
	/**
	 * 添加角色
	 * @param role
	 * @return List
	 */
	public void saveRole(Role role){
		try{
			role.setCreateDate(new Date());
			role.setCreater(AdminConstant.getSessionUser());
			roleDao.save(role);
		}catch(Exception ex){
			throw new OAException("添加角色时出现异常！", ex);
		}
	}
	/**
	 * 根据主键id获取角色
	 * @param id 主键
	 * @return 角色
	 */
	public Role getRole(Long id){
		try{
			return roleDao.get(Role.class, id);
		}catch(Exception ex){
			throw new OAException("根据主键id获取角色时出现异常！", ex);
		}
	}
	/**
	 * 修改角色
	 * @param role 角色
	 */
	public void updateRole(Role role){
		try{
			/** 获取持久化状态的角色 */
			Role r = getRole(role.getId());
			r.setModifier(AdminConstant.getSessionUser());
			r.setModifyDate(new Date());
			r.setName(role.getName());
			r.setRemark(role.getRemark());
		}catch(Exception ex){
			throw new OAException("修改角色时出现异常！", ex);
		}
	}
	/**
	 * 删除角色
	 * @param ids
	 */
	public void deleteRole(String[] ids){
		try{
			roleDao.deleteRole(ids);
		}catch(Exception ex){
			throw new OAException("删除角色时出现异常！", ex);
		}
	}
	
	
	/** TODO############################# 操作管理业务处理 ################################## */
	/**
	 * 加载操作树
	 * @return List
	 */
	public List<Map<String, Object>> loadModuleTree(){
		try{
			 // [{id: '', name:''},{id:'', name:''}]
			 List<Map<String, Object>> moduleLists = moduleDao.getModuleByCodeAndName();
			 // [{id : '', pid: '', name : ''},{},...]
			 /** 迭代List集合 */
			 for (Map<String, Object> map : moduleLists){
				 /** 获取code */
				 String code = map.get("id").toString();
				 /** 计算pid */
				 String pid = code.length() == CODE_LEN ? "0" : code.substring(0, code.length() - CODE_LEN);
				 map.put("pid", pid);
				 
				 /** 获取name */
				 String name = map.get("name").toString();
				 map.put("name", name.replaceAll("-", ""));
			 }
			 return moduleLists;
		}catch(Exception ex){
			throw new OAException("加载操作树时出现异常！", ex);
		}
	}
	/**
	 * 分页查询操作
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @return 操作集合
	 */
	public List<Module> getModuleByPage(String parentCode, PageModel pageModel){
		try{
			List<Module> modules = moduleDao.getModuleByPage(parentCode, pageModel, CODE_LEN);
			for (Module m : modules){
				if (m.getCreater() != null) m.getCreater().getName();
				if (m.getModifier() != null) m.getModifier().getName();
			}
			return modules;
		}catch(Exception ex){
			throw new OAException("分页查询操作时出现异常！", ex);
		}
	}
	/**
	 * 添加操作
	 * @param module 操作实体
	 */
	public void saveModule(Module module){
		try{
			/** 获取父级code */
			String parentCode = module.getCode();
			parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
			/** 生成主键列的值 */
			String code = generatorDao.generatorCode(Module.class, "code", CODE_LEN, parentCode);
			module.setCode(code); // 主键列
			module.setCreateDate(new Date());
			module.setCreater(AdminConstant.getSessionUser());
			module.setName(parentCode.replaceAll(".", "-") + module.getName());
			moduleDao.save(module);
		}catch(Exception ex){
			throw new OAException("添加操作时出现异常！", ex);
		}
	}
	/**
	 * 根据主键code查询操作
	 * @param code
	 * @return 操作实体
	 */
	public Module getModule(String code){
		try{
			return moduleDao.get(Module.class, code);
		}catch(Exception ex){
			throw new OAException("根据主键code查询操作时出现异常！", ex);
		}
	}
	/**
	 * 修改操作
	 * @param module 操作实体
	 */
	public void updateModule(Module module){
		try{
			Module m = getModule(module.getCode());
			m.setModifier(AdminConstant.getSessionUser());
			m.setModifyDate(new Date());
			/** 获取父级code */
			String parentCode = m.getCode().substring(0, m.getCode().length() - CODE_LEN);
			m.setName(parentCode.replaceAll(".", "-") + module.getName());
			m.setUrl(module.getUrl());
			m.setRemark(module.getRemark());
		}catch(Exception ex){
			throw new OAException("修改操作时出现异常！", ex);
		}
	}
	/**
	 * 批量删除操作
	 * @param codes
	 */
	public void deleteModule(String[] codes){
		try{
			moduleDao.deleteModule(codes);
		}catch(Exception ex){
			throw new OAException("批量删除操作时出现异常！", ex);
		}
	}
	
	/** TODO############################# 角色绑定业务处理 ################################## */
	/**
	 * 根据角色id分页查询用户(把这个角色中的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPageAndRoleId(Long id, PageModel pageModel){
		try{
			List<User> users = userDao.getUserByPageAndRoleId(id, pageModel);
			for (User u : users){
				if (u.getJob() != null) u.getJob().getName();
				if (u.getDept() != null) u.getDept().getName();
				if (u.getCreater() != null) u.getCreater().getName();
				if (u.getChecker() != null) u.getChecker().getName();
			}
			return users;
		}catch(Exception ex){
			throw new OAException("根据角色id分页查询用户(把这个角色中的用户查询出来)时出现异常！", ex);
		}
	}
	/**
	 * 根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来)
	 * @param id 角色id
	 * @param pageModel 分页实体
	 * @return 用户集合
	 */
	public List<User> getUserByPageNotRoleId(Long id, PageModel pageModel){
		try{
			List<User> users = userDao.getUserByPageNotRoleId(id, pageModel);
			for (User u : users){
				if (u.getJob() != null) u.getJob().getName();
				if (u.getCreater() != null) u.getCreater().getName();
			}
			return users;
		}catch(Exception ex){
			throw new OAException("根据角色id分页查询用户(把这个角色中没有绑定的用户查询出来)时出现异常！", ex);
		}
	}
	/**
	 * 角色绑定用户
	 * @param id 角色id
	 * @param userIds 用户id
	 */
	public void bindUser(Long id, String[] userIds){
		try{
			/** 用户与角色这两个持久化类，看哪边维护中间表(操作中间表) 没有加mappedBy属性的就可以维护中间表 */
			Role role = getRole(id); // 持久化状态
			/** 获取该角色已有的用户 */
			Set<User> users = role.getUsers();
			/** 循环添加新的用户 */
			for (String userId : userIds){
				User u = new User();
				u.setUserId(userId);
				users.add(u);
			}
			role.setUsers(users);
		}catch(Exception ex){
			throw new OAException("角色绑定用户时出现异常！", ex);
		}
	}
	/**
	 * 角色解除用户
	 * @param id 角色id
	 * @param userIds 用户id
	 */
	public void unBindUser(Long id, String[] userIds){
		try{
			/** 用户与角色这两个持久化类，看哪边维护中间表(操作中间表) 没有加mappedBy属性的就可以维护中间表 */
			Role role = getRole(id); // 持久化状态
			/** 获取该角色已有的用户 */
			Set<User> users = role.getUsers();
			/** 循环所有的用户id */
			for (String userId : userIds){
				User u = new User();
				u.setUserId(userId);
				users.remove(u);
			}
			role.setUsers(users);
		}catch(Exception ex){
			throw new OAException("角色解除用户时出现异常！", ex);
		}
	}
	/**
	 * 异步加载权限树
	 * @return List
	 */
	public List<Map<String, Object>> loadPopedomTree(){
		try{
			List<Map<String, Object>> dataLists = moduleDao.getModuleByCodeAndName(CODE_LEN * 2);
			for (Map<String, Object> map : dataLists){
				String code = map.get("id").toString();
				map.put("pid", code.length() == CODE_LEN ? "0" : code.substring(0, code.length() - CODE_LEN));
				map.put("name", map.get("name").toString().replaceAll("-", ""));
			}
			return dataLists;
		}catch(Exception ex){
			throw new OAException("异步加载权限树时出现异常！", ex);
		}
	}
	/**
	 * 查询权限(12位操作)
	 * @param moduleCode 8位code
	 * @return List (Module中的code是12位)
	 */
	public List<Module> getModulesByCode8(String moduleCode){
		try{
			return moduleDao.getModulesByCode8(CODE_LEN, moduleCode);
		}catch(Exception ex){
			throw new OAException("查询权限(12位操作)时出现异常！", ex);
		}
	}
	/**
	 * 角色绑定操作
	 * @param role 角色
	 * @param moduleCode 八位的code
	 * @param codes 12位的code
	 */
	public void bindModule(Role role, String moduleCode, String codes){
		try{
			
			/** 先根据模块代号与角色id删除以前绑定的权限 */
			popedomDao.delete(role.getId(), moduleCode);
			
			if (!StringUtils.isEmpty(codes)){
				/** 创建模块 */
				Module module = new Module();
				module.setCode(moduleCode);
				for (String code : codes.split(",")){
					Popedom popedom = new Popedom();
					popedom.setCreateDate(new Date());
					popedom.setCreater(AdminConstant.getSessionUser());
					popedom.setModule(module);
					Module opera = new Module();
					opera.setCode(code);
					popedom.setOpera(opera);
					popedom.setRole(role);
					popedomDao.save(popedom);
				}
			}
		}catch(Exception ex){
			throw new OAException("角色绑定操作时出现异常！", ex);
		}
	}
	/**
	 * 查询该角色在这个模块中已经绑定的操作
	 * @param id 角色id
	 * @param moduleCode 模块代号
	 * @return 操作的集合
	 */
	public List<String> getOperaCodeByRoleIdAndModuleCode(Long id, String moduleCode){
		try{
			return popedomDao.getOperaCodeByRoleIdAndModuleCode(id, moduleCode);
		}catch(Exception ex){
			throw new OAException("查询该角色在这个模块中已经绑定的操作时出现异常！", ex);
		}
	}
	/**
	 * 根据当前登录用户生成功能菜单树
	 * @return List
	 */
	public List<Map<String, Object>> loadMenuTree(){
		try{
			/** 根据当前登录用户查询他所有能操作的模块代号 */
			List<String> moduleCodeLists = popedomDao.getModuleCodeByUserId(AdminConstant.getSessionUser().getUserId());
			// [00010001, 00010002, 00010003,
			//  00020001, 00020002, 00020003, 00020004,
			//  00030001, 00030002, 
			//  00040001, 00040002]
			// [{id : '', pid: '', name : '', url : ''},{}]
			List<Map<String, Object>> dataLists = new ArrayList<Map<String, Object>>();
			
			/** 定义父级code */
			String parentCode = null;
			
			for (String moduleCode : moduleCodeLists){
				// {id : '', pid: '', name : '', url : ''} 
				/** ################## code为八位生成树节点 ###################### */
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", moduleCode);
				map.put("pid", moduleCode.substring(0, moduleCode.length() - CODE_LEN));
				/** 根据主键列查询模块对象 */
				Module module = getModule(moduleCode);
				map.put("name", module.getName().replaceAll("-", ""));
				map.put("url", module.getUrl());
				/** 添加Map集合 */
				dataLists.add(map);
				
				if (parentCode != null && moduleCode.startsWith(parentCode)){
					continue;
				}
				
				/** ################## code为四位生成树的父节点 ###################### */
				parentCode = moduleCode.substring(0, moduleCode.length() - CODE_LEN); // 四位
				map = new HashMap<String, Object>();
				map.put("id", parentCode);
				map.put("pid", "0");
				/** 根据主键列查询模块对象 */
				module = getModule(parentCode);
				map.put("name", module.getName());
				map.put("url", "");
				/** 添加Map集合 */
				dataLists.add(map);
			}
			return dataLists;
		}catch(Exception ex){
			throw new OAException("根据当前登录用户生成功能菜单树时出现异常！", ex);
		}
	}
	/**
	 * 找回密码
	 * @param userId
	 * @param question
	 * @param answer
	 * @return
	 */
	public String findPwd(String userId, int question, String answer){
		try{
			String tip = "密码找回失败！";
			if (!StringUtils.isEmpty(userId)){
				User user = getUser(userId, false);
				if (user != null && user.getQuestion() == question 
						&& user.getAnswer().equals(answer)){
					/** 生成新密码 */
					String newPwd = UUID.randomUUID().toString()
							.replaceAll("-", "").substring(0, 6);
					String message = "亲，您的新密码为：<font color='red'>"+ newPwd +"</font>";
					/** 发送邮件 */
					boolean isSuccess = emailSender.send(user.getEmail(), "办公管理系统-找回密码", message, true);
					if (isSuccess){
						user.setPassWord(MD5.getMD5(newPwd));
						tip = "您的新密码已发送到：" + user.getEmail() + ",请及时修改！";
					}
				}
			}
			return tip;
		}catch(Exception ex){
			throw new OAException("找回密码时出现异常！", ex);
		}
	}
	/**
	 * 修改密码
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @param okpwd 确认密码
	 * @return true成功  false失败
	 */
	public boolean updatePwd(String oldpwd, String newpwd, String okpwd){
		try{
			/** 判断新密码与确认密码是否一致 */
			if (!StringUtils.isEmpty(newpwd) && newpwd.equals(okpwd)){
				/** 判断旧密码 */
				User user = AdminConstant.getSessionUser();
				if (user.getPassWord().equals(MD5.getMD5(oldpwd))){
					User u = userDao.get(User.class, user.getUserId());
					u.setPassWord(MD5.getMD5(newpwd));
					return true;
				}
			}
			return false;
		}catch(Exception ex){
			throw new OAException("修改密码时出现异常！", ex);
		}
	}
}