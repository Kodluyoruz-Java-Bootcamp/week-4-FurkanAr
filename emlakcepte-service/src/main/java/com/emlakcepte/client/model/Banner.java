package com.emlakcepte.client.model;

public class Banner {

	private String bannerNo;
	private int piece;
	private String phone1;
	private String phone2;
	private String address;
	
	public Banner() {
		
	}
	
	public Banner(String bannerNo, int piece, String phone1, String phone2, String address) {
		super();
		this.bannerNo = bannerNo;
		this.piece = piece;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
	}

	public String getBannerNo() {
		return bannerNo;
	}

	public void setBannerNo(String bannerNo) {
		this.bannerNo = bannerNo;
	}

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
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
		return "Banner [bannerNo=" + bannerNo + ", piece=" + piece + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", address=" + address + "]";
	}
	
	
	
	
}
