package org.adidas.code.challange.rest.producer.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.adidas.code.challange.rest.producer.graph.Edge;
import org.adidas.code.challange.rest.producer.graph.Graph;
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
public class GraphServiceTest {

	private static Logger logger = LoggerFactory.getLogger(GraphServiceTest.class);

	@Mock
	private CityDistanceRepository cityDistanceRepository;

	@Mock
	private CityRepository cityRepository;

	@InjectMocks
	private GraphService graphService;

	public static Iterable<City> getCityListAll() {
		List<City> cityList = new ArrayList<>();
		cityList.add(new City("MAD", "Madrid"));
		cityList.add(new City("BCN", "Barcelona"));
		cityList.add(new City("PAR", "Paris"));
		return cityList;
	}

	public static List<CityDistance> getCityDistanceListLessAll() {
		List<CityDistance> cityDistanceList = new ArrayList<>();
		cityDistanceList.add(new CityDistance("id1", new City("MAD", "Madrid"), new City("BCN", "Barcelona"), 1));
		cityDistanceList.add(new CityDistance("id2", new City("MAD", "Madrid"), new City("PAR", "Paris"), 1));
		cityDistanceList.add(new CityDistance("id3", new City("BCN", "Barcelona"), new City("MAD", "Madrid"), 1));
		cityDistanceList.add(new CityDistance("id4", new City("PAR", "Paris"), new City("MAD", "Madrid"), 1));
		cityDistanceList.add(new CityDistance("id5", new City("PAR", "Paris"), new City("BCN", "Barcelona"), 1));
		cityDistanceList.add(new CityDistance("id6", new City("BCN", "Barcelona"), new City("PAR", "Paris"), 1));
		return cityDistanceList;
	}

	public static List<CityDistance> getCityDistanceListAll() {
		List<CityDistance> cityDistanceList = new ArrayList<>();
		cityDistanceList.add(new CityDistance("id1", new City("MAD", "Madrid"), new City("BCN", "Barcelona"), 600));
		cityDistanceList.add(new CityDistance("id2", new City("MAD", "Madrid"), new City("PAR", "Paris"), 1500));
		cityDistanceList.add(new CityDistance("id3", new City("BCN", "Barcelona"), new City("MAD", "Madrid"), 600));
		cityDistanceList.add(new CityDistance("id4", new City("PAR", "Paris"), new City("MAD", "Madrid"), 1500));
		cityDistanceList.add(new CityDistance("id5", new City("PAR", "Paris"), new City("BCN", "Barcelona"), 1000));
		cityDistanceList.add(new CityDistance("id6", new City("BCN", "Barcelona"), new City("PAR", "Paris"), 1000));
		return cityDistanceList;
	}

	@Test
	public void getGraphLessStepsTest() {
		// prepare
		Mockito.when(cityRepository.findAll()).thenReturn(getCityListAll());
		Mockito.when(cityDistanceRepository.findAll()).thenReturn(getCityDistanceListLessAll());
		// test
		Graph graphLess = graphService.getGraphLessSteps();
		logger.info("Test getGraphLessStepsTest: " + graphLess);
		assertEquals(getGraphLess(), graphLess);
	}

	@Test
	public void getGraphShortDistanceTest() {
		// prepare
		Mockito.when(cityRepository.findAll()).thenReturn(getCityListAll());
		Mockito.when(cityDistanceRepository.findAll()).thenReturn(getCityDistanceListAll());
		// test
		Graph graphLess = graphService.getGraphShortDistance();
		logger.info("Test getGraphShortDistanceTest: " + graphLess);
		assertEquals(getGraphShort(), graphLess);
	}

	public static Graph getGraphLess() {
		List<Vertex> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		nodes.add(new Vertex("MAD", "Madrid"));
		nodes.add(new Vertex("BCN", "Barcelona"));
		nodes.add(new Vertex("PAR", "Paris"));
		edges.add(new Edge("id1", new Vertex("MAD", "Madrid"), new Vertex("BCN", "Barcelona"), 1));
		edges.add(new Edge("id2", new Vertex("MAD", "Madrid"), new Vertex("PAR", "Paris"), 1));
		edges.add(new Edge("id3", new Vertex("BCN", "Barcelona"), new Vertex("MAD", "Madrid"), 1));
		edges.add(new Edge("id4", new Vertex("PAR", "Paris"), new Vertex("MAD", "Madrid"), 1));
		edges.add(new Edge("id5", new Vertex("PAR", "Paris"), new Vertex("BCN", "Barcelona"), 1));
		edges.add(new Edge("id6", new Vertex("BCN", "Barcelona"), new Vertex("PAR", "Paris"), 1));
		return new Graph(nodes, edges);
	}

	public static Graph getGraphShort() {
		List<Vertex> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		nodes.add(new Vertex("MAD", "Madrid"));
		nodes.add(new Vertex("BCN", "Barcelona"));
		nodes.add(new Vertex("PAR", "Paris"));
		edges.add(new Edge("id1", new Vertex("MAD", "Madrid"), new Vertex("BCN", "Barcelona"), 600));
		edges.add(new Edge("id2", new Vertex("MAD", "Madrid"), new Vertex("PAR", "Paris"), 1500));
		edges.add(new Edge("id3", new Vertex("BCN", "Barcelona"), new Vertex("MAD", "Madrid"), 600));
		edges.add(new Edge("id4", new Vertex("PAR", "Paris"), new Vertex("MAD", "Madrid"), 1500));
		edges.add(new Edge("id5", new Vertex("PAR", "Paris"), new Vertex("BCN", "Barcelona"), 1000));
		edges.add(new Edge("id6", new Vertex("BCN", "Barcelona"), new Vertex("PAR", "Paris"), 1000));
		return new Graph(nodes, edges);
	}

}
