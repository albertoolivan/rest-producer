package org.adidas.code.challange.rest.producer.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.exception.ExceptionResponseDTO;
import org.adidas.code.challange.rest.producer.graph.DijkstraAlgorithm;
import org.adidas.code.challange.rest.producer.graph.Edge;
import org.adidas.code.challange.rest.producer.graph.Graph;
import org.adidas.code.challange.rest.producer.graph.IntineraryDTO;
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

	private Graph graph = null;

	public City getCity(String id) {
		City city = null;
		Optional<City> cityOptional = cityRepository.findById(id);
		if (cityOptional.isPresent()) {
			city = cityOptional.get();
		}
		return city;
	}

	public IntineraryDTO getItineraryShort(String cityOriginId, String cityDestinationId) {
		IntineraryDTO intineraryDTO = new IntineraryDTO();

		City cityOrigin = getCity(cityOriginId);
		if (cityOrigin == null) {
			throw new ExceptionResponseDTO("CityOriginId " + cityOriginId + " not found!");
		}
		City cityDestinaton = getCity(cityDestinationId);
		if (cityDestinaton == null) {
			throw new ExceptionResponseDTO("CityDestinationId " + cityDestinationId + " not found!");
		}

		Vertex vertexOrigin = new Vertex(cityOrigin.getId(), cityOrigin.getName());
		Vertex vertexDFestination = new Vertex(cityDestinaton.getId(), cityDestinaton.getName());

		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(getGraph());
		dijkstra.execute(vertexOrigin);
		LinkedList<Vertex> path = dijkstra.getPath(vertexDFestination);
		int sumPathWeight = dijkstra.sumPathWeight(path);

		intineraryDTO.setPath(path);
		intineraryDTO.setSumPathWeight(sumPathWeight);

		return intineraryDTO;
	}

	public Graph getGraph() {
		if (graph != null) {
			return graph;
		} else {
			List<Vertex> nodes = new ArrayList<Vertex>();
			List<Edge> edges = new ArrayList<Edge>();

			Iterable<City> cityList = cityRepository.findAll();
			for (City city : cityList) {
				Vertex vertex = new Vertex(city.getId(), city.getName());
				nodes.add(vertex);
			}

			Iterable<CityDistance> cityDistanceList = cityDistanceRepository.findAll();
			for (CityDistance cityDistance : cityDistanceList) {
				Vertex vertexOrigin = new Vertex(cityDistance.getCityOrigin().getId(),
						cityDistance.getCityOrigin().getName());
				Vertex vertexDestination = new Vertex(cityDistance.getCityDestination().getId(),
						cityDistance.getCityDestination().getName());
				Edge edge = new Edge(cityDistance.getId(), vertexOrigin, vertexDestination,
						cityDistance.getDistanceKm());
				edges.add(edge);
			}
			graph = new Graph(nodes, edges);
			return graph;
		}

	}

}
