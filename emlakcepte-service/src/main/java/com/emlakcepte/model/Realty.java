package com.emlakcepte.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.emlakcepte.model.enums.Category;
import com.emlakcepte.model.enums.RealtyKind;
import com.emlakcepte.model.enums.RealtyType;

@Entity
@Table(name = "realty")
public class Realty {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "realty_no", nullable = false)
	private Integer no;
	@Column(name = "title", nullable = false, length = 200)
	private String title;
	@Column(name = "create_date")
	private LocalDateTime createDate;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "packet_id", referencedColumnName = "id", nullable = false)
	private Packet packet;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private RealtyType status;
	@Column(name = "province")
	private String province;
	@Column(name = "district")
	private String district;
	@Enumerated(EnumType.STRING)
	private Category category;
	@Enumerated(EnumType.STRING)
	private RealtyKind type;

	public Realty() {
	}

	public Realty(Integer no, String title, LocalDateTime createDate, RealtyType status) {
		super();
		this.no = no;
		this.title = title;
		this.createDate = createDate;
		this.status = status;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	@Override
	public String toString() {
		return "Realty [no=" + no + ", title=" + title + ", createDate=" + createDate + ", status=" + getStatus()
				+ ", province=" + province + "]";

	}

}
