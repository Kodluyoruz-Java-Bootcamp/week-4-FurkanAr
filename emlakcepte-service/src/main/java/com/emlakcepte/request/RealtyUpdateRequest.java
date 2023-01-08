package com.emlakcepte.request;

import com.emlakcepte.model.enums.RealtyType;

public class RealtyUpdateRequest {
	private RealtyType status;

	public RealtyType getStatus() {
		return status;
	}

	public void setStatus(RealtyType status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RealtyUpdateRequest [status=" + status + "]";
	}


}
