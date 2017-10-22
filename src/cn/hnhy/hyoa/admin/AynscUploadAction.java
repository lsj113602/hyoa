package cn.hnhy.hyoa.admin;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 表单异步上传
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年5月7日 下午5:46:47
 */
public class AynscUploadAction extends ActionSupport {
	
	private static final long serialVersionUID = -546168947470913437L;
	private String remark;
	private File pic;
	private String picFileName;
	
	@Override
	public String execute()  {
		try{
			System.out.println("remark: " + remark);
			/** 获取项目的部署路径 */
			String path = ServletActionContext.getServletContext().getRealPath("/images/pic");
			File dir = new File(path);
			/** 判断目录是否存在 */
			if (!dir.exists()){
				dir.mkdirs();
			}
			/** 生成新的文件名 */
			String newFileName = UUID.randomUUID().toString() + 
						"." + FilenameUtils.getExtension(picFileName); 
			/** 文件拷贝 */
			FileUtils.copyFile(pic, new File(path + "/" + newFileName));
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().print("/images/pic/" + newFileName);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return NONE;
	}


	/** setter and getter method */
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getPic() {
		return pic;
	}
	public void setPic(File pic) {
		this.pic = pic;
	}
	public String getPicFileName() {
		return picFileName;
	}
	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}
}