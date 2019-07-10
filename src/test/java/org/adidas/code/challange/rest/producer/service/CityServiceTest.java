package org.adidas.code.challange.rest.producer.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.dto.ArrivalTimeDTO;
import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.graph.Vertex;
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
		intineraryDTOExpected.setDepartureTime(LocalDateTime.of(2019, 07, 10, 1, 30));
		intineraryDTOExpected.setArrivalTime(LocalDateTime.of(2019, 07, 10, 14, 0));
		intineraryDTOExpected.setDurationTime("12:30");
		Mockito.when(cityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new City("MAD", "Madrid")))
				.thenReturn(Optional.of(new City("PAR", "Paris")));
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList1())
				.thenReturn(getCityDistanceList2());
		Mockito.when(graphService.getGraphShortDistance()).thenReturn(GraphServiceTest.getGraphShort());
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryShortDistance("MAD", "PAR",
				LocalDateTime.of(2019, 7, 10, 01, 30));
		logger.info("Test - getItineraryShortTest: " + intineraryDTO);
		assertEquals(intineraryDTOExpected, intineraryDTO);
	}

	@Test
	public void getItineraryShortTest2() {
		IntineraryDTO intineraryDTOExpected = new IntineraryDTO();
		intineraryDTOExpected.setMessage(
				"CityOriginId MAD and CityDestinationId MAD are same city, please choose other destination.");
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryShortDistance("MAD", "MAD",
				LocalDateTime.of(2019, 7, 10, 01, 30));
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
		intineraryDTOExpected.setSumPathWeight(1500);
		intineraryDTOExpected.setDepartureTime(LocalDateTime.of(2019, 7, 10, 1, 30));
		intineraryDTOExpected.setArrivalTime(LocalDateTime.of(2019, 7, 10, 14, 0));
		intineraryDTOExpected.setDurationTime("12:30");
		Mockito.when(cityRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new City("MAD", "Madrid")))
				.thenReturn(Optional.of(new City("PAR", "Paris")));
		Mockito.when(cityDistanceRepository.findByCityOriginId(Mockito.anyString())).thenReturn(getCityDistanceList1())
				.thenReturn(getCityDistanceList2());
		// here we mock both graphs, first to calculate less and second to calculate
		// real distance with short graph (with weight)
		Mockito.when(graphService.getGraphLessSteps()).thenReturn(GraphServiceTest.getGraphLess());
		Mockito.when(graphService.getGraphShortDistance()).thenReturn(GraphServiceTest.getGraphShort());
		// test
		IntineraryDTO intineraryDTO = cityService.getItineraryLessSteps("MAD", "PAR",
				LocalDateTime.of(2019, 7, 10, 01, 30));
		logger.info("Test - getItineraryLessTest: " + intineraryDTO);
		assertEquals(intineraryDTOExpected, intineraryDTO);
	}

	@Test
	public void calculateArrivalTimeTest() {
		// prepare
		LocalDateTime departureTime1 = null;
		int distanceKm1 = 110;
		int speed1 = 120;
		ArrivalTimeDTO arrivalTimeDTOCheck1 = new ArrivalTimeDTO();
		// test
		ArrivalTimeDTO arrivalTimeDTO1 = cityService.calculateArrivalTime(departureTime1, distanceKm1, speed1);
		logger.info("Test - calculateArrivalTime(1): " + arrivalTimeDTO1);
		assertEquals(arrivalTimeDTOCheck1, arrivalTimeDTO1);

		// prepare
		LocalDateTime departureTime2 = LocalDateTime.of(2019, 7, 10, 01, 30);
		int distanceKm2 = 0;
		int speed2 = 120;
		ArrivalTimeDTO arrivalTimeDTOCheck2 = new ArrivalTimeDTO();
		// test
		ArrivalTimeDTO arrivalTimeDTO2 = cityService.calculateArrivalTime(departureTime2, distanceKm2, speed2);
		logger.info("Test - calculateArrivalTime(2): " + arrivalTimeDTO2);
		assertEquals(arrivalTimeDTOCheck2, arrivalTimeDTO2);

		// prepare
		LocalDateTime departureTime3 = LocalDateTime.of(2019, 7, 10, 01, 30);
		int distanceKm3 = 500;
		int speed3 = 0;
		ArrivalTimeDTO arrivalTimeDTOCheck3 = new ArrivalTimeDTO();
		// test
		ArrivalTimeDTO arrivalTimeDTO3 = cityService.calculateArrivalTime(departureTime3, distanceKm3, speed3);
		logger.info("Test - calculateArrivalTime(3): " + arrivalTimeDTO3);
		assertEquals(arrivalTimeDTOCheck3, arrivalTimeDTO3);

		// prepare
		LocalDateTime departureTime4 = LocalDateTime.of(2019, 7, 10, 01, 30);
		int distanceKm4 = 500;
		int speed4 = 120;
		ArrivalTimeDTO arrivalTimeDTOCheck4 = new ArrivalTimeDTO();
		arrivalTimeDTOCheck4.setArrivalTime(LocalDateTime.of(2019, 7, 10, 5, 39));
		arrivalTimeDTOCheck4.setDurationTime("04:09");
		// test
		ArrivalTimeDTO arrivalTimeDTO4 = cityService.calculateArrivalTime(departureTime4, distanceKm4, speed4);
		logger.info("Test - calculateArrivalTime(4): " + arrivalTimeDTO4);
		assertEquals(arrivalTimeDTOCheck4, arrivalTimeDTO4);

		// prepare
		LocalDateTime departureTime5 = LocalDateTime.of(2019, 7, 10, 01, 30);
		int distanceKm5 = 1500;
		int speed5 = 120;
		ArrivalTimeDTO arrivalTimeDTOCheck5 = new ArrivalTimeDTO();
		arrivalTimeDTOCheck5.setArrivalTime(LocalDateTime.of(2019, 7, 10, 14, 00));
		arrivalTimeDTOCheck5.setDurationTime("12:30");
		// test
		ArrivalTimeDTO arrivalTimeDTO5 = cityService.calculateArrivalTime(departureTime5, distanceKm5, speed5);
		logger.info("Test - calculateArrivalTime(5): " + arrivalTimeDTO5);
		assertEquals(arrivalTimeDTOCheck5, arrivalTimeDTO5);
	}

	@Test
	public void calculateRealPathDistanceTest() {
		// prepare
		Mockito.when(graphService.getGraphShortDistance()).thenReturn(GraphServiceTest.getGraphShort());
		LinkedList<Vertex> path1 = new LinkedList<>();
		int distanceCheck1 = 0;
		// test
		int distance1 = cityService.calculateRealPathDistance(path1);
		logger.info("Test - calculateRealPathDistance(1): " + distance1);
		assertEquals(distanceCheck1, distance1);
		
		// prepare
		LinkedList<Vertex> path2 = new LinkedList<>();
		path2.add(new Vertex("MAD", "Madrid"));
		path2.add(new Vertex("BCN", "Barcelona"));
		int distanceCheck2 = 600;
		// test
		int distance2 = cityService.calculateRealPathDistance(path2);
		logger.info("Test - calculateRealPathDistance(2): " + distance2);
		assertEquals(distanceCheck2, distance2);
	}

}
