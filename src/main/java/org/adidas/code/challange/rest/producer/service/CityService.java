package org.adidas.code.challange.rest.producer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.dto.ArrivalTimeDTO;
import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.graph.DijkstraAlgorithm;
import org.adidas.code.challange.rest.producer.graph.Graph;
import org.adidas.code.challange.rest.producer.graph.Vertex;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CityService {

	private static Logger logger = LoggerFactory.getLogger(CityService.class);

	// vehicle speed get from application.yml
	// default is 120 (car)
	@Value("${vehicle.speed:120}")
	private int vehicleSpeed = 120;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CityDistanceRepository cityDistanceRepository;

	@Autowired
	GraphService graphService;

	@Autowired
	CityDistanceService cityDistanceService;

	/**
	 * Find in database a City from id and return a CityDTO. Include CityDistance
	 * info.
	 * 
	 * @param id
	 * @return CityDTO
	 */
	public CityDTO getCity(String id) {
		CityDTO cityDTO = null;
		Optional<City> cityOptional = cityRepository.findById(id);
		if (cityOptional.isPresent()) {
			City city = cityOptional.get();
			List<CityDistance> cityDistanceList = cityDistanceRepository.findByCityOriginId(city.getId());
			cityDTO = new CityDTO(city.getId(), city.getName(),
					cityDistanceService.convertCityDistanceList(cityDistanceList));
		}
		return cityDTO;
	}

	/**
	 * Load all City in List<CityDTO>
	 * 
	 * @param id
	 * @return List<CityDTO>
	 */
	public List<CityDTO> getCityAll() {
		List<CityDTO> cityDTOList = new ArrayList<>();
		Iterable<City> cityIterable = cityRepository.findAll();
		for (City city : cityIterable) {
			cityDTOList.add(new CityDTO(city.getId(), city.getName()));
		}
		return cityDTOList;
	}

	/**
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with short
	 * distance of kilometers.
	 * 
	 * Call DijkstraAlgorithm.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param departureTime
	 * @return IntineraryDTO
	 */
	public IntineraryDTO getItineraryShortDistance(String cityOriginId, String cityDestinationId,
			LocalDateTime departureTime) {
		return getItinerary(cityOriginId, cityDestinationId, departureTime, graphService.getGraphShortDistance());
	}

	/**
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with less steps.
	 * 
	 * Call DijkstraAlgorithm.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param departureTime
	 * @return IntineraryDTO
	 */
	public IntineraryDTO getItineraryLessSteps(String cityOriginId, String cityDestinationId,
			LocalDateTime departureTime) {
		return getItinerary(cityOriginId, cityDestinationId, departureTime, graphService.getGraphLessSteps());
	}

	/**
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with graph
	 * loaded.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param departureTime
	 * @param graph
	 * @return IntineraryDTO
	 */
	private IntineraryDTO getItinerary(String cityOriginId, String cityDestinationId, LocalDateTime departureTime,
			Graph graph) {
		IntineraryDTO intineraryDTO = new IntineraryDTO();

		if (cityOriginId.equals(cityDestinationId)) {
			intineraryDTO.setMessage("CityOriginId " + cityOriginId + " and CityDestinationId " + cityDestinationId
					+ " are same city, please choose other destination.");
			return intineraryDTO;
		}
		CityDTO cityOrigin = getCity(cityOriginId);
		if (cityOrigin == null) {
			intineraryDTO.setMessage("CityOriginId " + cityOriginId + " not found, see valid cities in /city/all");
			return intineraryDTO;
		}
		CityDTO cityDestinaton = getCity(cityDestinationId);
		if (cityDestinaton == null) {
			intineraryDTO
					.setMessage("CityDestinationId " + cityDestinationId + " not found, see valid cities in /city/all");
			return intineraryDTO;
		}

		Vertex vertexOrigin = new Vertex(cityOrigin.getId(), cityOrigin.getName());
		Vertex vertexDestination = new Vertex(cityDestinaton.getId(), cityDestinaton.getName());

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(vertexOrigin);
		LinkedList<Vertex> path = dijkstra.getPath(vertexDestination);

		LinkedList<CityDTO> pathDTO = new LinkedList<>();
		for (Vertex vertex : path) {
			pathDTO.add(new CityDTO(vertex.getId(), vertex.getName()));
		}
		intineraryDTO.setPath(pathDTO);
		intineraryDTO.setDepartureTime(departureTime);

		// lessSteps algorithm use weight=1 to get path but no real distance
		int distanceKm = calculateRealPathDistance(path);
		intineraryDTO.setSumPathWeight(distanceKm);
		ArrivalTimeDTO arrivalTimeDTO = calculateArrivalTime(departureTime, distanceKm, vehicleSpeed);
		intineraryDTO.setDurationTime(arrivalTimeDTO.getDurationTime());
		intineraryDTO.setArrivalTime(arrivalTimeDTO.getArrivalTime());
		intineraryDTO.setMessage("Itinerary found successfull.");

		return intineraryDTO;
	}

	/**
	 * Calculate arrival time with departure time, distance(km) and speed(km/h)
	 * 
	 * @param departureTime
	 * @param distanceKm
	 * @param speed
	 * @return LocalDateTime
	 */
	public ArrivalTimeDTO calculateArrivalTime(LocalDateTime departureTime, int distanceKm, int speed) {
		logger.info("Calculate arrivalTime from departureTime {}, distance {} and speed {}", departureTime, distanceKm,
				speed);
		ArrivalTimeDTO arrivalTimeDTO = new ArrivalTimeDTO();
		LocalDateTime arrivalTime = departureTime;
		if (departureTime != null && distanceKm > 0 && speed > 0) {
			// time(h) = space(km) / speed(km/h)
			double duration = (double) distanceKm / (double) speed;
			// get hour by trunk float
			int durationHours = (int) duration;
			// get minute by last 2 decimals
			duration = duration - durationHours;
			duration = duration * 100;
			// trunk again to get minutes/base100
			int durationMinutes = (int) duration;
			// convert base/100 into minutes base/60
			durationMinutes = (durationMinutes * 60) / 100;
			// add hours and minutes from departure time
			arrivalTime = arrivalTime.plusHours(durationHours);
			arrivalTime = arrivalTime.plusMinutes(durationMinutes);
			// convert to string and format "4:9" into "04:09" HH:mm
			String durationMinutesString = String.valueOf(durationMinutes);
			if (durationMinutes < 10) {
				durationMinutesString = "0" + durationMinutesString;
			}
			String durationHoursString = String.valueOf(durationHours);
			if (durationHours < 10) {
				durationHoursString = "0" + durationHoursString;
			}
			logger.info("Duration {}:{} -> arrivalTime {} ", durationHoursString, durationMinutesString, arrivalTime);
			arrivalTimeDTO.setArrivalTime(arrivalTime);
			arrivalTimeDTO.setDurationTime(durationHoursString + ":" + durationMinutesString);
		}
		return arrivalTimeDTO;
	}

	/**
	 * Calculate real distance from graphs of less steps.
	 * 
	 * @param cityDTOPath
	 * @return int
	 */
	public int calculateRealPathDistance(LinkedList<Vertex> path) {
		int realDistance = 0;
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graphService.getGraphShortDistance());
		realDistance = dijkstra.sumPathWeight(path);
		return realDistance;
	}

}
