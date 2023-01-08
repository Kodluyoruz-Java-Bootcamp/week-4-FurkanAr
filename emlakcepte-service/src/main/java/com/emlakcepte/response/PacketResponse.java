package com.emlakcepte.response;

import java.time.LocalDate;

import com.emlakcepte.model.enums.PacketStatus;

public class PacketResponse {
	private Integer id;
	private Integer userId;
	private Double price;
	private LocalDate createDate;
	private LocalDate expireDate;
	private PacketStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public PacketStatus getStatus() {
		return status;
	}

	public void setStatus(PacketStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PacketResponse [id=" + id + ", userId=" + userId + ", price=" + price + ", createDate=" + createDate
				+ ", expireDate=" + expireDate + ", status=" + status + "]";
	}

}
