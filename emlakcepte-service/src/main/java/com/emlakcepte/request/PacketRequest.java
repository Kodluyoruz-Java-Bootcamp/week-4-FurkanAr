package com.emlakcepte.request;

public class PacketRequest {
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PacketRequest [userId=" + userId + "]";
	}

}
