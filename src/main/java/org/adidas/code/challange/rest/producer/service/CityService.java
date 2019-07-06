package org.adidas.code.challange.rest.producer.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.exception.ExceptionResponseDTO;
import org.adidas.code.challange.rest.producer.graph.DijkstraAlgorithm;
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

	public CityDTO getCity(String id) {
		CityDTO cityDTO = null;
		Optional<City> cityOptional = cityRepository.findById(id);
		if (cityOptional.isPresent()) {
			City city = cityOptional.get();
			List<CityDistance> cityDistanceList = cityDistanceRepository.findByCityOriginId(city.getId());
			System.out.println("cityDistanceList: " + cityDistanceList);
			cityDTO = new CityDTO(city.getId(), city.getName(),
					cityDistanceService.convertCityDistanceList(cityDistanceList));
		}
		return cityDTO;
	}

	public IntineraryDTO getItineraryShort(String cityOriginId, String cityDestinationId) {
		IntineraryDTO intineraryDTO = new IntineraryDTO();

		CityDTO cityOrigin = getCity(cityOriginId);
		if (cityOrigin == null) {
			throw new ExceptionResponseDTO("CityOriginId " + cityOriginId + " not found!");
		}
		CityDTO cityDestinaton = getCity(cityDestinationId);
		if (cityDestinaton == null) {
			throw new ExceptionResponseDTO("CityDestinationId " + cityDestinationId + " not found!");
		}

		Vertex vertexOrigin = new Vertex(cityOrigin.getId(), cityOrigin.getName());
		Vertex vertexDFestination = new Vertex(cityDestinaton.getId(), cityDestinaton.getName());

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graphService.getGraph());
		dijkstra.execute(vertexOrigin);
		LinkedList<Vertex> path = dijkstra.getPath(vertexDFestination);
		int sumPathWeight = dijkstra.sumPathWeight(path);

		LinkedList<CityDTO> pathDTO = new LinkedList<>();
		for (Vertex vertex : path) {
			pathDTO.add(new CityDTO(vertex.getId(), vertex.getName()));
		}
		intineraryDTO.setPath(pathDTO);
		intineraryDTO.setSumPathWeight(sumPathWeight);

		return intineraryDTO;
	}

}
