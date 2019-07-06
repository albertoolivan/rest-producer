package org.adidas.code.challange.rest.producer.service;

import java.util.ArrayList;
import java.util.List;

import org.adidas.code.challange.rest.dto.CityDistanceDTO;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityDistanceService {

	@Autowired
	CityDistanceRepository cityDistanceRepository;

	/**
	 * Find in database a list of CityDistance with cityOriginId.
	 * Convert into DTOs.
	 * 
	 * @param cityDistanceOriginId
	 * @return List<CityDistanceDTO>
	 */
	public List<CityDistanceDTO> getCityDistanceListByOriginId(String cityDistanceOriginId) {
		List<CityDistance> cityDistanceList = cityDistanceRepository.findByCityOriginId(cityDistanceOriginId);
		return convertCityDistanceList(cityDistanceList);

	}

	/**
	 * Convert a List<CityDistance> into List<CityDistanceDTO>.
	 * 
	 * @param cityDistanceList
	 * @return List<CityDistanceDTO>
	 */
	public List<CityDistanceDTO> convertCityDistanceList(List<CityDistance> cityDistanceList) {
		List<CityDistanceDTO> cityDistanceDTOList = new ArrayList<>();
		for (CityDistance cityDistance : cityDistanceList) {
			cityDistanceDTOList.add(new CityDistanceDTO(cityDistance.getId(), cityDistance.getCityOrigin().getId(),
					cityDistance.getCityOrigin().getName(), cityDistance.getCityDestination().getId(),
					cityDistance.getCityDestination().getName(), cityDistance.getDistanceKm()));
		}
		return cityDistanceDTOList;
	}

}
