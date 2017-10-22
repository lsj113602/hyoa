package cn.hnhy.hyoa.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hnhy.hyoa.admin.addressbook.service.AddressbookService;
import cn.hnhy.hyoa.admin.identity.dao.UserDao;
import cn.hnhy.hyoa.admin.identity.service.IdentityService;
import cn.hnhy.hyoa.admin.identity.service.impl.IdentityServiceImpl;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 异步请求的控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年3月4日 下午2:28:40
 */
public class LoginAjax extends ActionSupport {
	
	private static final long serialVersionUID = 7047266005602477464L;
	/** 定义Field接收页面请求参数 */
	private String userId;
	private String password;
	private String vcode;
	private int key;
	/** 注入业务层处理接口 */
	@Resource
	private IdentityService identityService;
	

	/** 定义Map集合封装响应数据 */
	private Map<String, Object> responseData;
	
	@Override
	public String execute() {
		try{
			// {"tip" : "登录成功!", "status" : 0}
			// Map集合 
			responseData = identityService.login(userId, password, vcode, key);
			//System.out.println(JSONUtil.serialize(responseData));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	
	/** setter method */
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getVcode() {
		return vcode;
	}


	public void setVcode(String vcode) {
		this.vcode = vcode;
	}


	public int getKey() {
		return key;
	}


	public void setKey(int key) {
		this.key = key;
	}


	public Map<String, Object> getResponseData() {
		return responseData;
	}


	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}


	
}