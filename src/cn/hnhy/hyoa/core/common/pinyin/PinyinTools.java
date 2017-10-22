package cn.hnhy.hyoa.core.common.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 汉字转拼音工具类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午3:27:22
 */
public final class PinyinTools {
	/**
	 * 汉字转拼音方法
	 * @param str 汉字
	 * @return 拼音
	 */
	public static String toPinyin(String str){
		String res = "";
		for (int i = 0; i < str.length(); i++){
			char temp = str.charAt(i);
			String[] arr = PinyinHelper.toHanyuPinyinStringArray(temp);
			res += arr[0];
		}
		return res.replaceAll("\\d+", "");
	}
	
	public static void main(String[] args) {
		System.out.println(toPinyin("李天一"));
		// 李 674e
		System.out.println(Integer.toHexString('李'));
		// unicode
		System.out.println('\u674e');
	}
}
