package cn.hnhy.hyoa.admin.identity.dao;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.HibernateDao;

/**
 * 职位数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:46:46
 */
public interface JobDao extends HibernateDao {
	/**
	 * 加载职位
	 * @return List 集合
	 */
	List<Map<String, Object>> getJobByCodeAndName();

	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	List<Job> getJobByPage(PageModel pageModel);

	/**
	 * 批量删除职位
	 * @param codes
	 */
	void deleteJob(String[] codes);


	
}
