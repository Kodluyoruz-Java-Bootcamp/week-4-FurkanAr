package com.emlakcepte.response;


public class PaymentResponse {
	private Integer id;
	private Integer userId;
	private String name;
	private String email;
	private Integer packetId;
	private Double price;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "PaymentResponse [id=" + id + ", userId=" + userId + ", name=" + name + ", email=" + email
				+ ", packetId=" + packetId + ", price=" + price + "]";
	}

}
