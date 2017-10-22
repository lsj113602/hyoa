package cn.hnhy.hyoa.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hnhy.hyoa.core.dao.GeneratorDao;

/**
 * 通用的主键生成器数据访问接口实现类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-8-24 上午11:51:54
 * @version 1.0
 */
public class GeneratorDaoImpl extends HibernateDaoImpl implements GeneratorDao {

	/**
	 * 主键生成的方法
	 * @param clazz 持久化类
	 * @param field 字段
	 * @param codeLen 长度(生成的位数)
	 * @param parentCode (父级code)
	 * @return 主键code值
	 */
	public String generatorCode(Class<?> clazz, String field, int codeLen, String parentCode){
		// SELECT MAX(CODE) FROM `oa_id_module` WHERE LENGTH(CODE) = 4  // 没传父级code
        // SELECT MAX(CODE) FROM `oa_id_module` WHERE LENGTH(CODE) = 8 AND CODE LIKE '0001%'  // 传父级code 0001
		StringBuilder hql = new StringBuilder();
		hql.append("select max("+ field +") from " + clazz.getSimpleName());
		hql.append(" where length("+ field +") = ? ");
		
		parentCode = StringUtils.isEmpty(parentCode) ? "" : parentCode;
		List<Object> params = new ArrayList<Object>();
		params.add(parentCode.length() + codeLen);
		
		if (!StringUtils.isEmpty(parentCode)){
			hql.append(" and " + field + " like ?");
			params.add(parentCode + "%");
		}
		/** 查询得到最大的code */
		String maxCode =  this.findUniqueEntity(hql.toString(), params.toArray());
		/** 如果最大的code为空 */
		if (maxCode == null){
			// 0001
			// 00050001
			String suffix = "";
			for (int i = 1; i < codeLen; i++){
				suffix += "0";
			}
			return parentCode + suffix + 1; // 0001
		}else{
			// 0004 --> 0005
			// 00010003 --> 00010004
			/** 截取最后四位  */
			String tempCode = maxCode.substring(maxCode.length() - codeLen, maxCode.length());
			// 0004 --> 5
			// 0009 --> 10
			String suffix = String.valueOf(Integer.valueOf(tempCode) + 1);
			if (suffix.length() > codeLen){
				throw new RuntimeException("主键生成已越界！");
			}
			return parentCode + tempCode.substring(0, tempCode.length() - suffix.length()) + suffix;
		}
	}
}