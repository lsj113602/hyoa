package cn.hnhy.hyoa.core.common.web;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import cn.hnhy.hyoa.core.common.security.MD5;

/**
 * 操作Cookie的工具类
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月7日 下午8:17:49
 */
public final class CookieTools {
	/**
	 * 获取Cookie
	 * @param cookieName cookie名称
	 * @return Cookie
	 */
	public static Cookie getCookie(String cookieName){
		/** 获取当前用户浏览器中所有的Cookie */
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (cookies != null && cookies.length > 0){
			/** 迭代所有的Cookie */
			for (Cookie cookie : cookies){
				if (cookie.getName().equals(cookieName)){
					return cookie;
				}
			}
		}
		return null;
	}
	/**
	 * 添加Cookie
	 * @param cookieName cookie名称
	 * @param cookieValue cookie值
	 * @param maxAge 最大有效时间(按秒算)
	 */
	public static void addCookie(String cookieName, String cookieValue, int maxAge) throws Exception{
		/** 获取Cookie */
		Cookie cookie = getCookie(cookieName);
		if (cookie == null){
			cookie = new Cookie(cookieName, MD5.getMD5(cookieValue));
		}
		/** 设置Cookie的失效时间 */
		cookie.setMaxAge(maxAge);
		/** 设置Cookie访问路径 http://localhost:8080/ */
		cookie.setPath("/");
		/** 设置Cookie可以跨域访问: http://www.itcast.cn http://t.itcast.cn */
		//cookie.setDomain(".itcast.cn");
		/** 把Cookie添加到用户的浏览器 */
		ServletActionContext.getResponse().addCookie(cookie);
	}
	
	/**
	 * 删除cookie
	 * @param cookieName cookie名称
	 */
	public static void removeCookie(String cookieName){
		/** 获取Cookie */
		Cookie cookie = getCookie(cookieName);
		if (cookie != null){
			/** 设置Cookie的失效时间(立即失效) */
			cookie.setMaxAge(0);
			/** 设置Cookie访问路径 http://localhost:8080/ */
			cookie.setPath("/");
			/** 把Cookie添加到用户的浏览器 */
			ServletActionContext.getResponse().addCookie(cookie);
		}
	}
}
