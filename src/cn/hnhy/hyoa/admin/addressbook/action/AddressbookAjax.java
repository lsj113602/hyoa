package cn.hnhy.hyoa.admin.addressbook.action;

import java.util.List;
import java.util.Map;

/**
 * 通讯录异步请求处理控制器
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月2日 下午10:52:11
 */
public class AddressbookAjax extends AddressbookAction {
	
	private static final long serialVersionUID = 168102282560028660L;
	private List<Map<String, Object>> responseData;
	
	/**  异步加载联系组生成树 */
	public String loadContactGroupTreeAjax(){
		try{
			responseData = addressbookService.loadContactGroupTree();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	/** setter and getter method */
	public List<Map<String, Object>> getResponseData() {
		return responseData;
	}
	public void setResponseData(List<Map<String, Object>> responseData) {
		this.responseData = responseData;
	}
}
