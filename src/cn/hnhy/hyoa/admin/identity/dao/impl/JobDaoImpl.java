package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.List;
import java.util.Map;

import cn.hnhy.hyoa.admin.identity.dao.JobDao;
import cn.hnhy.hyoa.admin.identity.entity.Job;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 职位数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:42:00
 */
public class JobDaoImpl extends HibernateDaoImpl implements JobDao {
	/**
	 * 加载职位
	 * @return List 集合
	 */
	public List<Map<String, Object>> getJobByCodeAndName(){
		return this.find("select new map(code as code, name as name) from Job order by code asc");
	}
	/**
	 * 分页查询职位
	 * @param pageModel
	 * @return
	 */
	public List<Job> getJobByPage(PageModel pageModel){
		return this.findByPage("from Job order by code asc", pageModel, null);
	}
	/**
	 * 批量删除职位
	 * @param codes
	 */
	public void deleteJob(String[] codes){
		// DELETE FROM `oa_id_employee` WHERE employee_id IN(?,?,?)
		// DML风格
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Job where code in(");
		for (int i = 0; i < codes.length; i++){
			hql.append(i == 0 ? "?" : ",?");
		}
		hql.append(")");
		this.bulkUpdate(hql.toString(), codes);
	}
}
