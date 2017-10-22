package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.entity.Module;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 模块数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:47:15
 */
public interface ModuleDao extends HibernateDao {
	/**
	 * 查询操作(code与name)
	 * @return List
	 */
	List<Map<String, Object>> getModuleByCodeAndName();
	/**
	 * 分页查询操作
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @return 操作集合
	 */
	List<Module> getModuleByPage(String parentCode, PageModel pageModel,int codeLen);
	/**
	 * 批量删除操作
	 * @param codes
	 */
	void deleteModule(String[] codes);
	/**
	 * 查询操作(code与name)
	 * @param len 长度
	 * @return List
	 */
	List<Map<String, Object>> getModuleByCodeAndName(int len);
	/**
	 * 查询权限(12位操作)
	 * @param moduleCode 8位code
	 * @return List (Module中的code是12位)
	 */
	List<Module> getModulesByCode8(int codeLen, String moduleCode);

}
