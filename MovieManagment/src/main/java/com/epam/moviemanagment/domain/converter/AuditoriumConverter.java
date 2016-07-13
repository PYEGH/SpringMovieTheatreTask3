package com.epam.moviemanagment.domain.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

public class AuditoriumConverter {

	public static Auditorium toDto(final AuditoriumEntity entity) {
		final Auditorium dto = new Auditorium();
		dto.setName(entity.getName());
		dto.setNumberOfSeats(entity.getNumberOfSeats());
		dto.setVipSeats(entity.getVipSeats());

		return dto;
	}

	public static AuditoriumEntity toEntity(final Auditorium dto) {
		final AuditoriumEntity entity = new AuditoriumEntity();
		entity.setName(dto.getName());
		entity.setNumberOfSeats(dto.getNumberOfSeats());
		entity.setVipSeats(dto.getVipSeats());

		return entity;
	}
	
	public static Set<Long> convertStringWithVipSeatsToLongList(final String stringWithVipSeates){
		final Set<Long> vipSeats = new HashSet<>();
		final Scanner sc = new Scanner(stringWithVipSeates);
		sc.useDelimiter(",");
		
		while(sc.hasNext()){
			final Long seat = Long.parseLong(sc.next());
			vipSeats.add(seat);
		}
		return vipSeats;
	}
}
