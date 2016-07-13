package com.epam.moviemanagment.domain.converter;

import java.util.Calendar;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.epam.moviemanagment.domain.dto.Auditorium;
import com.epam.moviemanagment.domain.dto.Event;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;
import com.epam.moviemanagment.domain.entity.EventEntity;

public class EventConverter {
	
	public static Event toDto(final EventEntity entity) {
		final Event dto = new Event();
		dto.setId(entity.getId());
		dto.setBasePrice(entity.getBasePrice());
		dto.setName(entity.getName());
		dto.setRating(entity.getRating());
		dto.setAirDates(entity.getAirDates());

		final NavigableMap<Calendar, Auditorium> auditoriums = new TreeMap<>();
		final NavigableMap<Calendar, AuditoriumEntity> auditoriumEntities = entity.getAuditoriums();
		for (final Calendar date : auditoriumEntities.keySet()) {
			final AuditoriumEntity auditoriumEntity = auditoriumEntities.get(date);
			final Auditorium auditoriumDto = AuditoriumConverter.toDto(auditoriumEntity);
			auditoriums.put(date,auditoriumDto);
		}
		dto.setAuditoriums(auditoriums);

		return dto;
	}
	
	public static EventEntity toEntity(final Event dto){
		final EventEntity entity = new EventEntity();
		entity.setId(dto.getId());
		entity.setBasePrice(dto.getBasePrice());
		entity.setName(dto.getName());
		entity.setRating(dto.getRating());
		entity.setAirDates(dto.getAirDates());
		
		final NavigableMap<Calendar, Auditorium> auditoriums = dto.getAuditoriums();
		final NavigableMap<Calendar, AuditoriumEntity> auditoriumEntities =  new TreeMap<>();
		for(final Calendar date :auditoriums.keySet()){
			final Auditorium auditoriumDto = auditoriums.get(date);
			final AuditoriumEntity auditoriumEntity = AuditoriumConverter.toEntity(auditoriumDto);
			auditoriumEntities.put(date, auditoriumEntity);
		}
		entity.setAuditoriums(auditoriumEntities);
		
		return entity;
	}
}
