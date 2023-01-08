package com.emlakcepte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banner")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Integer id;
	@Column(name = "banner_no")
	private String bannerNo;
	@Column(name = "piece")
	private Integer piece;
	@Column(name = "phone1")
	private String phone1;
	@Column(name = "phone2")
	private String phone2;
	@Column(name = "address")
	private String address;

	public Banner() {
		super();
	}

	public Banner(String bannerNo, Integer piece, String phone1, String phone2, String address) {
		super();
		this.bannerNo = bannerNo;
		this.piece = piece;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
	}

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
		return "Banner [id=" + id + ", bannerNo=" + bannerNo + ", piece=" + piece + ", phone1=" + phone1 + ", phone2="
				+ phone2 + ", address=" + address + "]";
	}

}
