package com.emlakcepte.converter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emlakcepte.model.Packet;
import com.emlakcepte.model.enums.PacketStatus;
import com.emlakcepte.response.PacketResponse;

@Component
public class PacketConverter {
	private static final double PACKET_PRICE = 50.00;

	public PacketResponse convert(Packet packet) {
		PacketResponse response = new PacketResponse();
		response.setId(packet.getId());
		response.setPrice(packet.getPrice());
		response.setCreateDate(packet.getCreateDate());
		response.setExpireDate(packet.getExpireDate());
		response.setStatus(packet.getStatus());
		response.setUserId(packet.getUser().getId());
		return response;
	}


	public Packet convert() {
		Packet packet = new Packet();
		packet.setPrice(PACKET_PRICE);
		packet.setStatus(PacketStatus.PASSIVE);
		return packet;
	}
	
	
	public Packet addDates(Packet packet) {
		packet.setCreateDate(LocalDate.now());
		packet.setExpireDate(LocalDate.now().plusMonths(1));
		packet.setStatus(PacketStatus.USING);
		return packet;
	}
	
	public Packet update(Packet packet) {
		Long intervalDays = ChronoUnit.DAYS.between(LocalDate.now(), packet.getExpireDate());
		packet.setExpireDate(packet.getExpireDate().plusDays(intervalDays));
		System.out.println("daysssssss: "+intervalDays);
		return packet;
	}
	

	public List<PacketResponse> convert(List<Packet> packetList) {
		List<PacketResponse> packetResponses = new ArrayList<>();
		packetList.stream().forEach(packet -> packetResponses.add(convert(packet)));
		return packetResponses;
	}

}
