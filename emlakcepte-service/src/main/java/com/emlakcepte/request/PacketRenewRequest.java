package com.emlakcepte.request;

public class PacketRenewRequest {
	private Integer userId;
	private Integer packetId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	@Override
	public String toString() {
		return "PacketRenewRequest [userId=" + userId + ", packetId=" + packetId + "]";
	}

}
