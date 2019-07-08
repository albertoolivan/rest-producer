package org.adidas.code.challange.rest.producer.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

public class DijkstraRealTest {

	@Test
	public void testExcuteShort1() {
		// prepare
		Graph graph = GraphUtil.getGraphShort();
		System.out.println("graphShort1: " + graph.toString());
		int resultSize = 4;
		int weightExpected = 1670;
		Vertex originVertex = new Vertex("SEV", "Sevilla");
		Vertex resultVertex = new Vertex("PAR", "Paris");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Short1) SEV -> PAR | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteLess1() {
		// prepare
		Graph graph = GraphUtil.getGraphShort();
		System.out.println("graphLess1: " + graph.toString());
		int resultSize = 4;
		int weightExpected = 1670;
		Vertex originVertex = new Vertex("SEV", "Sevilla");
		Vertex resultVertex = new Vertex("PAR", "Paris");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Less1) SEV -> PAR | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteShort2() {
		// prepare
		Graph graph = GraphUtil.getGraphShort();
		System.out.println("grahShort2: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 820;
		Vertex originVertex = new Vertex("SEV", "Lisboa");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Short2) SEV -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteLess2() {
		// prepare
		Graph graph = GraphUtil.getGraphLess();
		System.out.println("graphLess2: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 2;
		Vertex originVertex = new Vertex("SEV", "Lisboa");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Less2) SEV -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteShort3() {
		// prepare
		Graph graph = GraphUtil.getGraphShort();
		System.out.println("grahShort3: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 540;
		Vertex originVertex = new Vertex("BIL", "Bilbao");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Short3) BIL -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	@Test
	public void testExcuteLess3() {
		// prepare
		Graph graph = GraphUtil.getGraphLess();
		System.out.println("graphShort3: " + graph.toString());
		int resultSize = 3;
		int weightExpected = 2;
		Vertex originVertex = new Vertex("BIL", "Bilbao");
		Vertex resultVertex = new Vertex("BCN", "Barcelona");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Less3) BIL -> BCN | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	// This case (4) the result is different because of distance
	// BIL - PAR is 1400 alone, and with BIL - ZGZ - BCN- PAR is 1390
	@Test
	public void testExcuteShort4() {
		// prepare
		Graph graph = GraphUtil.getGraphShort();
		System.out.println("grahReal4: " + graph.toString());
		int resultSize = 4;
		int weightExpected = 1390;
		Vertex originVertex = new Vertex("BIL", "Bilbao");
		Vertex resultVertex = new Vertex("PAR", "Paris");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Short4) BIL -> PAR | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}
	
	// this case short way with weight=1 is direct BIL-PAR
	@Test
	public void testExcuteLess4() {
		// prepare
		Graph graph = GraphUtil.getGraphLess();
		System.out.println("grahLess4: " + graph.toString());
		int resultSize = 2;
		int weightExpected = 1;
		Vertex originVertex = new Vertex("BIL", "Bilbao");
		Vertex resultVertex = new Vertex("PAR", "Paris");
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(originVertex);
		LinkedList<Vertex> path = dijkstra.getPath(resultVertex);
		int weight = dijkstra.sumPathWeight(path);
		System.out.println("Test(Less4) BIL -> PAR | " + path + " weight " + weight);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
		assertEquals(weightExpected, weight);
	}

}