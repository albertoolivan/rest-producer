package org.adidas.code.challange.rest.producer.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

public class DijkstraRealTest {

	@Test
	public void testExcuteReal1() {
		// prepare
		Graph graph = GraphUtil.getGraphReal();
		System.out.println("grahReal1: " + graph.toString());
		int resultSize = 4;
		int weightExpected = 1670;
		Vertex originVertex = new Vertex("SEV", "Sevilla");
		Vertex resultVertex = new Vertex("PAR", "Paris");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Real1) SEV -> PAR | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteReal2() {
		// prepare
		Graph graph = GraphUtil.getGraphReal();
		System.out.println("grahReal2: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 820;
		Vertex originVertex = new Vertex("SEV", "Lisboa");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Real2) SEV -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteReal3() {
		// prepare
		Graph graph = GraphUtil.getGraphReal();
		System.out.println("grahReal3: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 540;
		Vertex originVertex = new Vertex("BIL", "Bilbao");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Real3) BIL -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}

}