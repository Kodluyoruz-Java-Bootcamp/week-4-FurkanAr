package com.emlakcepte.request;

import java.io.Serializable;

import com.emlakcepte.model.enums.Category;
import com.emlakcepte.model.enums.RealtyKind;

public class RealtyRequest implements Serializable {
	private Integer no;
	private String title;
	private String province;
	private String district;
	private RealtyKind type;
	private Integer userId;
	private Integer packetId;
	private Category category;

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public RealtyKind getType() {
		return type;
	}

	public void setType(RealtyKind type) {
		this.type = type;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	@Override
	public String toString() {
		return "RealtyRequest [no=" + no + ", title=" + title + ", province=" + province + ", district=" + district
				+ ", type=" + type + ", userId=" + userId + ", packetId=" + packetId + ", category=" + category + "]";
	}



	
	
}
