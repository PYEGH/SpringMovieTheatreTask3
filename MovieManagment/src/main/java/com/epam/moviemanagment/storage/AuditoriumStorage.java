package com.epam.moviemanagment.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.epam.moviemanagment.domain.converter.AuditoriumConverter;
import com.epam.moviemanagment.domain.entity.AuditoriumEntity;

/**
 * Class is simulate storage for Auditoriums.
 *
 */
public class AuditoriumStorage {
	
	private static Set<AuditoriumEntity> auditoriums = new HashSet<>();

	static{
		final List<Properties> properties = new ArrayList<>();
		final Properties prop1 = getProperty("./src/main/resources/auditorium1.properties");
		final Properties prop2 = getProperty("./src/main/resources/auditorium2.properties");
		properties.add(prop1);
		properties.add(prop2);
		
		for(final Properties prop: properties){
			final AuditoriumEntity aud = new AuditoriumEntity();
			final String name = prop.getProperty("name");
			final Long numberOfSeat = Long.parseLong(prop.getProperty("numberOfSeats"));
			final Set<Long> vipSeats = AuditoriumConverter.convertStringWithVipSeatsToLongList(prop1.getProperty("vipSeats"));
			
			aud.setName(name);
			aud.setNumberOfSeats(numberOfSeat);
			aud.setVipSeats(vipSeats);
			auditoriums.add(aud);
		}
	}
	
	public static Set<AuditoriumEntity> getAuditoriums() {
		return auditoriums;
	}

	public static void setAuditoriums(Set<AuditoriumEntity> auditoriums) {
		AuditoriumStorage.auditoriums = auditoriums;
	}
	
	private static Properties getProperty(final String fileName){
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(fileName);			
			prop.load(input);			
			return prop;
			
		}catch (final IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
