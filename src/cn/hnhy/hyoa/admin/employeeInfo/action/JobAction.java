package cn.hnhy.hyoa.admin.employeeInfo.action;

import java.util.List;

import cn.hnhy.hyoa.admin.identity.entity.Job;

public class JobAction extends EmployeeInfoAction {
	private Job job;
	private List<Job> jobs;
	private String codes;
	private String tip;

	/** 分页查询职位 */
	public String selectJob(){
		try{
			jobs = employeeInfoService.getJobByPage(pageModel);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**添加职位*/
	public String addJob(){
		try{
			employeeInfoService.saveJob(job);
			setJob(null);
			setTip("添加成功！");
		}catch(Exception ex){
			setTip("添加失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**显示修改职位页面*/
	public String showUpdateJob(){
		try{
			job = employeeInfoService.getJob(job.getCode());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/**修改职位*/
	public String updateJob(){
		try{
			employeeInfoService.updateJob(job);
			setTip("修改成功！");
		}catch(Exception ex){
			setTip("修改失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	/** 删除职位 */
	public String deleteJob(){
		try{
			employeeInfoService.deleteJob(codes.split(","));
			setTip("删除成功！");
		}catch(Exception ex){
			setTip("删除失败！");
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*--------------geter and seter-------------*/

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	
}
