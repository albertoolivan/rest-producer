package org.adidas.code.challange.rest.producer.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

	private static Logger logger = LoggerFactory.getLogger(CityServiceTest.class);

	@Mock
	private CityDistanceRepository cityDistanceRepository;

	@Mock
	private CityRepository cityRepository;

	@InjectMocks
	private CityService cityService;

	@Mock
	private CityDistanceService cityDistanceService;

	@Mock
	private GraphService graphService;

	public static List<CityDistance> getCityDistanceList1() {
		List<CityDistance> cityDistanceList = new ArrayList<>();
		cityDistanceList.add(new CityDistance("id1", new City("MAD", "Madrid"), new City("BCN", "Barcelona"), 600));
		cityDistanceList.add(new CityDistance("id1", new City("MAD", "Madrid"), new City("PAR", "Paris"), 1500));
		return cityDistanceList;
	}

	public static List<CityDistance> getCityDistanceList2() {
		List<CityDistance> cityDistanceList = new ArrayList<>();
		cityDistanceList.add(new CityDistance("id1", new City("PAR", "Paris"), new City("BCN", "Barcelona"), 1000));
		cityDistanceList.add(new CityDistance("id1", new City("PAR", "Paris"), new City("MAD", "Madrid"), 1500));
		return cityDistanceList;
	}

	@Test
	public void getCityTest() {
		// prepare
		CityDTO cityDTOExpected = new CityDTO("MAD", "Madrid");
		Mockito.when(cityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new City("MAD", "Madrid")));
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList1());
		// test
		CityDTO cityDTO = cityService.getCity("MAD");
		logger.info("Test - getCity: " + cityDTO);
		assertEquals(cityDTOExpected, cityDTO);
	}

	@Test
	public void getCityAllTest() {
		// prepare
		CityDTO cityDTOExpected = new CityDTO("MAD", "Madrid");
		List<CityDTO> cityDTOListExpected = new ArrayList<>();
		cityDTOListExpected.add(cityDTOExpected);
		Iterable<City> cityIterable = Arrays.asList(new City("MAD", "Madrid"));
		Mockito.when(cityRepository.findAll()).thenReturn(cityIterable);
		// test
		List<CityDTO> cityDTOList = cityService.getCityAll();
		logger.info("Test - getCityAll: " + cityDTOList);
		assertEquals(cityDTOListExpected, cityDTOList);
	}

	@Test
	public void getItineraryShortTest() {
		// prepare
		IntineraryDTO intineraryDTOExpected = new IntineraryDTO();
		LinkedList<CityDTO> path = new LinkedList<>();
		path.add(new CityDTO("MAD", "Madrid"));
		path.add(new CityDTO("PAR", "Paris"));
		intineraryDTOExpected.setPath(path);
		intineraryDTOExpected.setMessage("Itinerary found successfull.");
		intineraryDTOExpected.setSumPathWeight(1500);
		Mockito.when(cityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new City("MAD", "Madrid")))
				.thenReturn(Optional.of(new City("PAR", "Paris")));
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList1())
				.thenReturn(getCityDistanceList2());
		Mockito.when(graphService.getGraphShortDistance()).thenReturn(GraphServiceTest.getGraphShort());
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryShortDistance("MAD", "PAR", LocalDateTime.of(2019, 7, 10, 01, 30));
		logger.info("Test - getItineraryShortTest: " + intineraryDTO);
		assertEquals(intineraryDTOExpected, intineraryDTO);
	}

	@Test
	public void getItineraryShortTest2() {
		IntineraryDTO intineraryDTOExpected = new IntineraryDTO();
		intineraryDTOExpected.setMessage(
				"CityOriginId MAD and CityDestinationId MAD are same city, please choose other destination.");
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryShortDistance("MAD", "MAD", LocalDateTime.of(2019, 7, 10, 01, 30));
		logger.info("Test - getItineraryShortTest2: " + intineraryDTO);
		assertEquals(intineraryDTOExpected, intineraryDTO);
	}

	@Test
	public void getItineraryLessTest() {
		IntineraryDTO intineraryDTOExpected = new IntineraryDTO();
		LinkedList<CityDTO> path = new LinkedList<>();
		path.add(new CityDTO("MAD", "Madrid"));
		path.add(new CityDTO("PAR", "Paris"));
		intineraryDTOExpected.setPath(path);
		intineraryDTOExpected.setMessage("Itinerary found successfull.");
		intineraryDTOExpected.setSumPathWeight(1);
		Mockito.when(cityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new City("MAD", "Madrid")))
				.thenReturn(Optional.of(new City("PAR", "Paris")));
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList1())
				.thenReturn(getCityDistanceList2());
		Mockito.when(graphService.getGraphLessSteps()).thenReturn(GraphServiceTest.getGraphLess());
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryLessSteps("MAD", "PAR", LocalDateTime.of(2019, 7, 10, 01, 30));
		logger.info("Test - getItineraryLessTest: " + intineraryDTO);
		assertEquals(intineraryDTOExpected, intineraryDTO);
	}

}
