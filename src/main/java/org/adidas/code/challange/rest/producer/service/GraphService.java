package org.adidas.code.challange.rest.producer.service;

import java.util.ArrayList;
import java.util.List;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.graph.Edge;
import org.adidas.code.challange.rest.producer.graph.Graph;
import org.adidas.code.challange.rest.producer.graph.Vertex;
import org.adidas.code.challange.rest.producer.repositories.CityDistanceRepository;
import org.adidas.code.challange.rest.producer.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

	// singleton graph
	private Graph graph = null;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CityDistanceRepository cityDistanceRepository;

	/**
	 * Get Graph from database (City and CityDistance). Used by DijkstraAlgorithm. 
	 * 
	 * @return Graph
	 */
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
