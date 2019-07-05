package org.adidas.code.challange.rest.producer.service;

import java.util.Optional;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
	
	@Autowired
	CityRepository cityRepository;
	
	public City getCity(String id) {
		City city = null;
		Optional<City> cityOptional = cityRepository.findById(id);
		if (cityOptional.isPresent()) {
			city = cityOptional.get();
		}
		return city;
	}

}
