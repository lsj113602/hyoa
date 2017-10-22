package cn.hnhy.hyoa.admin;

import javax.annotation.Resource;



import cn.hnhy.hyoa.admin.identity.service.IdentityService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 找回密码控制器
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2016-9-3 下午8:20:14
 * @version 1.0
 */
public class FindPwdAction extends ActionSupport {
	
	private static final long serialVersionUID = -2434074774229092310L;
	private String userId;
	private int question;
	private String answer;
	private String tip;
	@Resource
	private IdentityService identityService;
	
	@Override
	public String execute() {
		try{
			tip = identityService.findPwd(userId, question, answer);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}

	
	/** setter and getter method */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}