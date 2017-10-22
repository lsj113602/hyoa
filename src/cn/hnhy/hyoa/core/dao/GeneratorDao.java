package cn.hnhy.hyoa.core.dao;
/**
 * 通用的主键生成器数据访问接口
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年4月24日 下午10:07:01
 */
public interface GeneratorDao extends HibernateDao {
	/**
	 * 主键生成的方法
	 * @param clazz 持久化类
	 * @param field 字段
	 * @param codeLen 长度(生成的位数)
	 * @param parentCode (父级code)
	 * @return 主键code值
	 */
	String generatorCode(Class<?> clazz, String field, int codeLen, String parentCode);
}
