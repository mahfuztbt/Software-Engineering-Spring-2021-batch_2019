package com.mitonal.edu.common.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 抽象业务对象记录
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractDomainEntity implements Cloneable, Serializable {

	/**
	 * 创建日期
	 */
	@ApiModelProperty("实体创建时间")
	@CreatedDate
	protected Date createDate;

	/**
	 * 最后更新日期 Timestamp
	 */
	@ApiModelProperty("实体最后更新时间")
	@LastModifiedDate
	private Date modifyDate;

	/**
	 * 获取实体的id
	 * @return
	 */
	@JsonIgnore
	public Object getEntityId() {
		return null;
	}

	/**
	 * 克隆当前对象
	 * @return AbstractDomainEntity
	 * @throws CloneNotSupportedException
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
