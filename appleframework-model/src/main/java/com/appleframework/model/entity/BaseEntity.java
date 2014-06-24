package com.appleframework.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

/**
 * 实体类 - 基类
 */

@SuppressWarnings("serial")
public class BaseEntity implements Serializable {

	public static final String CREATE_TIME_PROPERTY_NAME = "createTime";// "创建日期"属性名称
	public static final String UPDATE_TIME_PROPERTY_NAME = "updateTime";// "修改日期"属性名称

	@XmlTransient
	protected Date createTime;// 创建日期
	
	@XmlTransient
	protected Date updateTime;// 修改日期

	@XmlTransient
	protected String createBy;// 创建人
	
	@XmlTransient
	protected String updateBy;// 修改人

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}