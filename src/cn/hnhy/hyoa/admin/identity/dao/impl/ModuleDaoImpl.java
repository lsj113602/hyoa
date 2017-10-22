package cn.hnhy.hyoa.admin.identity.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.admin.identity.dao.ModuleDao;
import cn.hnhy.hyoa.admin.identity.entity.Module;
import cn.hnhy.hyoa.core.common.web.PageModel;
import cn.hnhy.hyoa.core.dao.impl.HibernateDaoImpl;

/**
 * 模块数据访问接口实现类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午8:42:37
 */
@SuppressWarnings("unchecked")
public class ModuleDaoImpl extends HibernateDaoImpl implements ModuleDao {
	/**
	 * 查询操作(code与name)
	 * @return List
	 */
	public List<Map<String, Object>> getModuleByCodeAndName(){
		String hql = "select new map(code as id, name as name) from Module order by code asc";
		return this.find(hql);
	}
	/**
	 * 分页查询操作
	 * @param parentCode 父级code
	 * @param pageModel 分页实体
	 * @return 操作集合
	 */
	public List<Module> getModuleByPage(String parentCode, PageModel pageModel, int codeLen){
		// SELECT * FROM `oa_id_module` WHERE LENGTH(CODE) = 4;  // 没传code
		// SELECT * FROM `oa_id_module` WHERE LENGTH(CODE) = 8 AND CODE LIKE '0001%'    // 0001
		StringBuilder hql = new StringBuilder();
		hql.append("select m from Module m where length(code) = ?");
		parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
		/** 定义List集合封装查询参数 */
		List<Object> params = new ArrayList<Object>();
		params.add(parentCode.length() + codeLen);
		
		if (!StringUtils.isEmpty(parentCode)){
			hql.append(" and m.code like ?");
			params.add(parentCode + "%");
		}
		hql.append(" order by m.code asc");
		return this.findByPage(hql.toString(), pageModel, params);
	}
	/**
	 * 批量删除操作
	 * @param codes
	 */
	public void deleteModule(String[] codes){
		// 0001,0002
		// DELETE FROM `oa_id_module` WHERE CODE LIKE '0005%' OR LIKE '0006%'
		StringBuilder hql = new StringBuilder();
		hql.append("delete from Module where code like ?");
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < codes.length; i++){
			hql.append(i == 0 ? "" : " or code like ?");
			params.add(codes[i] + "%");
		}
		this.bulkUpdate(hql.toString(), params.toArray());
	}
	/**
	 * 查询操作(code与name)
	 * @param len 长度
	 * @return List
	 */
	public List<Map<String, Object>> getModuleByCodeAndName(int len){
		String hql = "select new map(code as id, name as name) from Module where length(code) <= ?";
		return (List<Map<String, Object>>)this.find(hql, new Object[]{len});
	}
	/**
	 * 查询权限(12位操作)
	 * @param moduleCode 8位code
	 * @return List (Module中的code是12位)
	 */
	public List<Module> getModulesByCode8(int codeLen, String moduleCode){
		String hql = "select m from Module m where length(m.code) = ? and m.code like ? order by m.code asc";
		return (List<Module>)this.find(hql, new Object[]{moduleCode.length() + codeLen, moduleCode + "%"});
	}
}
