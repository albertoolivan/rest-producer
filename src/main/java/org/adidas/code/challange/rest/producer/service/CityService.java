package org.adidas.code.challange.rest.producer.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.graph.DijkstraAlgorithm;
import org.adidas.code.challange.rest.producer.graph.Graph;
import org.adidas.code.challange.rest.producer.graph.Vertex;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CityDistanceRepository cityDistanceRepository;

	@Autowired
	GraphService graphService;

	@Autowired
	CityDistanceService cityDistanceService;

	/**
	 * Find in database a City from id and return a CityDTO.
	 * Include CityDistance info.
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
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with short distance of kilometers.
	 * 
	 * Call DijkstraAlgorithm.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @return IntineraryDTO
	 */
	public IntineraryDTO getItineraryShortDistance(String cityOriginId, String cityDestinationId) {
		return getItinerary(cityOriginId, cityDestinationId, graphService.getGraphShortDistance());
	}
	
	/**
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with less steps.
	 * 
	 * Call DijkstraAlgorithm.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @return IntineraryDTO
	 */
	public IntineraryDTO getItineraryLessSteps(String cityOriginId, String cityDestinationId) {
		return getItinerary(cityOriginId, cityDestinationId, graphService.getGraphLessSteps());
	}
	
	/**
	 * Get a IntineraryDTO from cityOriginId and cityDestinationId with graph loaded.
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param graph
	 * @return IntineraryDTO
	 */
	private IntineraryDTO getItinerary(String cityOriginId, String cityDestinationId, Graph graph) {
		IntineraryDTO intineraryDTO = new IntineraryDTO();

		if (cityOriginId.equals(cityDestinationId)) {
			intineraryDTO.setMessage("CityOriginId " + cityOriginId + " and CityDestinationId " + cityDestinationId + " are same city, please choose other destination.");
			return intineraryDTO;
		}
		CityDTO cityOrigin = getCity(cityOriginId);
		if (cityOrigin == null) {
			intineraryDTO.setMessage("CityOriginId " + cityOriginId + " not found, see valid cities in /city/all");
			return intineraryDTO;
		}
		CityDTO cityDestinaton = getCity(cityDestinationId);
		if (cityDestinaton == null) {
			intineraryDTO.setMessage("CityDestinationId " + cityDestinationId + " not found, see valid cities in /city/all");
			return intineraryDTO;
		}

		Vertex vertexOrigin = new Vertex(cityOrigin.getId(), cityOrigin.getName());
		Vertex vertexDestination = new Vertex(cityDestinaton.getId(), cityDestinaton.getName());
		
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(vertexOrigin);
		LinkedList<Vertex> path = dijkstra.getPath(vertexDestination);
		int sumPathWeight = dijkstra.sumPathWeight(path);

		LinkedList<CityDTO> pathDTO = new LinkedList<>();
		for (Vertex vertex : path) {
			pathDTO.add(new CityDTO(vertex.getId(), vertex.getName()));
		}
		intineraryDTO.setPath(pathDTO);
		intineraryDTO.setSumPathWeight(sumPathWeight);
		intineraryDTO.setMessage("Itinerary found successfull.");

		return intineraryDTO;
	}

}
