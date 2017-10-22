package cn.hnhy.hyoa.admin.identity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * 权限实体
 * @author moleef
 * @email moleef@foxmail.com
 * 2017年2月23日 下午5:22:06
 */
@Entity @Table(name="OA_ID_POPEDOM")
public class Popedom implements Serializable{
	
	private static final long serialVersionUID = 6064105672264545381L;
	/** ID	NUMBER	ID	PK(自增长) */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	/** MODULE_CODE	VARCHAR2(100)	模块代码	FK(OA_ID_MODULE)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="MODULE_CODE", referencedColumnName="CODE")
	private Module module;
	/** OPERA_CODE	VARCHAR2(100)	操作代码	FK(OA_ID_MODULE)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="OPERA_CODE", referencedColumnName="CODE")
	private Module opera;
	/** ROLE_ID	NUMBER	角色	FK(OA_ID_ROLE)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Role.class)
	@JoinColumn(name="ROLE_ID", referencedColumnName="ID")
	private Role role;
	/** CREATER	VARCHAR2(50)	创建人	FK(OA_ID_USER)*/
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID")
	private User creater;
	/** CREATE_DATE	DATE	创建时间	*/
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	/** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Module getOpera() {
		return opera;
	}
	public void setOpera(Module opera) {
		this.opera = opera;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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