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

	// singleton graph of short distance
	private Graph graphShortDistance = null;

	// singleton graph
	private Graph graphLessSteps = null;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	CityDistanceRepository cityDistanceRepository;

	/**
	 * Get Graph from database (City and CityDistance). Used by DijkstraAlgorithm.
	 * Load distanceKm from database to get short distance.
	 * 
	 * @return Graph
	 */
	public Graph getGraphShortDistance() {
		if (graphShortDistance != null) {
			return graphShortDistance;
		} else {
			graphShortDistance = createGraph(true);
			return graphShortDistance;
		}
	}

	/**
	 * Get Graph from database (City and CityDistance). Used by DijkstraAlgorithm.
	 * Set distanceKm = 1 to get less steps path.
	 * 
	 * @param setDistanceKm
	 * @return Graph
	 */
	public Graph getGraphLessSteps() {
		if (graphLessSteps != null) {
			return graphLessSteps;
		} else {
			graphLessSteps = createGraph(false);
		}
		return graphLessSteps;
	}

	/**
	 * Create Graph from database, assign distance depending on setDistanceKm.
	 * 
	 * setDistanceKm=true load distanceKm
	 * setDistanceKm=false set 1
	 * 
	 * @param setDistanceKm
	 * @return Graph
	 */
	private Graph createGraph(boolean setDistanceKm) {
		List<Vertex> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

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

			int distanceKm = 0;
			if (setDistanceKm) {
				// assign distance km for edges, Dijkstra will calculate shortest path
				distanceKm = cityDistance.getDistanceKm();
			} else {
				// assign 1 of each edge distance, Dijkstra will get always less steps path
				distanceKm = 1;
			}
			Edge edge = new Edge(cityDistance.getId(), vertexOrigin, vertexDestination, distanceKm);
			edges.add(edge);
		}
		return new Graph(nodes, edges);
	}
	
	public String resetGraph() {
		graphShortDistance = null;
		graphLessSteps = null;
		return "Reset graphs...";
	}

}
