package org.adidas.code.challange.rest.producer.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.adidas.code.challange.rest.dto.CityDistanceDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CityDistanceServiceTest {

	private static Logger logger = LoggerFactory.getLogger(CityDistanceServiceTest.class);

	@Mock
	private CityDistanceRepository cityDistanceRepository;

	@InjectMocks
	private CityDistanceService cityDistanceService;

	@Test
	public void getCityDistanceListByOriginIdTest() {
		// prepare
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList());
		// test
		List<CityDistanceDTO> check = cityDistanceService.getCityDistanceListByOriginId("MAD");
		logger.info("Test - getCityDistanceListByOriginId: " + check);
		assertEquals(getCityDistanceDTOList(), check);
	}

	@Test
	public void convertCityDistanceListTest() {
		// test
		List<CityDistanceDTO> check = cityDistanceService.convertCityDistanceList(getCityDistanceList());
		logger.info("Test - convertCityDistanceList: " + check);
		assertEquals(getCityDistanceDTOList(), check);
	}

	public static List<CityDistance> getCityDistanceList() {
		List<CityDistance> cityDistanceList = new ArrayList<>();
		cityDistanceList.add(new CityDistance("id", new City("MAD", "Madrid"), new City("PAR", "Paris"), 1500));
		cityDistanceList.add(new CityDistance("id", new City("MAD", "Madrid"), new City("BCN", "Barcelona"), 600));
		return cityDistanceList;
	}

	public static List<CityDistanceDTO> getCityDistanceDTOList() {
		List<CityDistanceDTO> cityDistanceDTOList = new ArrayList<>();
		cityDistanceDTOList.add(new CityDistanceDTO("id", "MAD", "Madrid", "PAR", "Paris", 1500));
		cityDistanceDTOList.add(new CityDistanceDTO("id", "MAD", "Madrid", "BCN", "Barcelona", 600));
		return cityDistanceDTOList;
	}

}
