package com.emlakcepte.response;

public class BannerResponse {
	private Integer id;
	private String bannerNo;
	private Integer piece;
	private String phone1;
	private String phone2;
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBannerNo() {
		return bannerNo;
	}

	public void setBannerNo(String bannerNo) {
		this.bannerNo = bannerNo;
	}

	public Integer getPiece() {
		return piece;
	}

	public void setPiece(Integer piece) {
		this.piece = piece;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "BannerResponse [id=" + id + ", bannerNo=" + bannerNo + ", piece=" + piece + ", phone1=" + phone1
				+ ", phone2=" + phone2 + ", address=" + address + "]";
	}

}
