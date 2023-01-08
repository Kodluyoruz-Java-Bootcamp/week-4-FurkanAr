package com.emlakcepte.response;

import java.time.LocalDateTime;

import com.emlakcepte.model.enums.Category;
import com.emlakcepte.model.enums.RealtyKind;
import com.emlakcepte.model.enums.RealtyType;

public class RealtyResponse {
	private Integer id;
	private Integer no;
	private String title;
	private LocalDateTime createDate;
	private Integer userId;
	private RealtyType status;
	private String province;
	private String district;
	private Category category;
	private RealtyKind type;
	private Integer packetId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public RealtyType getStatus() {
		return status;
	}

	public void setStatus(RealtyType status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public RealtyKind getType() {
		return type;
	}

	public void setType(RealtyKind type) {
		this.type = type;
	}
	
	

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	@Override
	public String toString() {
		return "RealtyResponse [id=" + id + ", no=" + no + ", title=" + title + ", createDate=" + createDate
				+ ", userId=" + userId + ", status=" + status + ", province=" + province + ", district=" + district
				+ ", category=" + category + ", type=" + type + ", packetId=" + packetId + "]";
	}

}
