package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.entity.Dept;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 部门数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:46:25
 */
public interface DeptDao extends HibernateDao {

	/**
	 * 加载部门
	 * @return List集合
	 */
	List<Map<String, Object>> getDeptByIdAndName();
	
	/**
	 * 分页查询部门
	 * @param pageModel
	 * @return
	 */
	List<Dept> getDeptByPage(PageModel pageModel);
	
	/**
	 * 批量删除部门
	 * @param ids
	 */
	void deleteDept(String[] ids);

}
