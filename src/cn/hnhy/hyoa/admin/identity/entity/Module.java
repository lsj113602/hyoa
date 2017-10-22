package cn.hnhy.hyoa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 模块实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:21:52
 */
@Entity @Table(name="OA_ID_MODULE")
public class Module implements Serializable{

	private static final long serialVersionUID = 8376327788945323792L;
	/**
	 * CODE	VARCHAR2(100)	代码	PK主键
	 * (0001...0002)四位为模块;
	 * (00010001..)八位为操作
	 */
	@Id @Column(name="CODE", length=100)
	private String code;
	/** NAME VARCHAR2(50) 名称	*/
	@Column(name="NAME", length=50)
	private String name;
	/** URL	VARCHAR2(100)	操作链接	*/
	@Column(name="URL", length=100)
	private String url;
	/** REMARK	VARCHAR2(500)	备注	*/
	@Column(name="REMARK", length=500)
	private String remark;
	/** MODIFIER	VARCHAR2(50) 修改人 FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID")
	private User modifier;
	/** MODIFY_DATE	DATE	修改时间 */
	@Column(name="MODIFY_DATE")
	private Date modifyDate;
	/** CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID")
	private User creater;
	/** CREATE_DATE	DATE	创建时间	*/
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	/** setter and getter method */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public User getModifier() {
		return modifier;
	}
	public void setModifier(User modifier) {
		this.modifier = modifier;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}