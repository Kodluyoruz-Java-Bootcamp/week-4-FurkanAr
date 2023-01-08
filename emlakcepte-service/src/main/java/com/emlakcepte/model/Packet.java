package com.emlakcepte.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.emlakcepte.model.enums.PacketStatus;

@Entity
@Table(name = "packet")
public class Packet {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "price")
	private Double price;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	@Column(name = "create_date")
	private LocalDate createDate;
	@Column(name = "expire_date")
	private LocalDate expireDate;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private PacketStatus status;
	@OneToMany(mappedBy="packet")
	private List<Realty> realtyList;

	public Packet() {
		super();
	}

	public Packet(User user) {
		super();
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<Realty> getRealtyList() {
		return realtyList;
	}

	public void setRealtyList(List<Realty> realtyList) {
		this.realtyList = realtyList;
	}

	@Override
	public String toString() {
		return "Packet [id=" + id + ", price=" + price + ", user=" + user + ", createDate=" + createDate
				+ ", expireDate=" + expireDate + ", status=" + status + "]";
	}

}
