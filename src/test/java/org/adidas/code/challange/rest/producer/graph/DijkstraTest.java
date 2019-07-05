package org.adidas.code.challange.rest.producer.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class DijkstraTest {

	@Test
	public void testExcute1() {
		// prepare
		Graph graph = GraphUtil.getGraphFull();
		System.out.println("grah1: " + graph.toString());
		int resultSize = 5;
		Vertex resultVertex = new Vertex("Node_10", "Node_10");
		List<Vertex> nodes = graph.getVertexes();
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		// origin 0 -> destination 10
		dijkstra.execute(nodes.get(0));
		// test 0 -> 10
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));
		System.out.println("Test(1) 0 -> 10 | " + path);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
	}

	@Test
	public void testExcute2() {
		// prepare
		Graph graph = GraphUtil.getGraphFull();
		System.out.println("grah2: " + graph.toString());
		int resultSize = 4;
		Vertex resultVertex = new Vertex("Node_9", "Node_9");
		List<Vertex> nodes = graph.getVertexes();
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		// origin 0 -> destination 9
		dijkstra.execute(nodes.get(0));
		// test 0 -> 9
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(9));
		System.out.println("Test(2) 0 -> 9 | " + path);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
	}

	@Test
	public void testExcute3() {
		// prepare
		Graph graph = GraphUtil.getGraphFull();
		System.out.println("grah3: " + graph.toString());
		int resultSize = 5;
		Vertex resultVertex = new Vertex("Node_10", "Node_10");
		List<Vertex> nodes = graph.getVertexes();
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		// origin 6 -> destination 10
		dijkstra.execute(nodes.get(6));
		// test 6 -> 10
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(10));
		System.out.println("Test(3) 6 -> 10 | " + path);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
	}

	@Test
	public void testExcute4() {
		// prepare
		Graph graph = GraphUtil.getGraphFull();
		System.out.println("grah4: " + graph.toString());
		int resultSize = 5;
		Vertex resultVertex = new Vertex("Node_6", "Node_6");
		List<Vertex> nodes = graph.getVertexes();
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		// origin 10 -> destination 6
		dijkstra.execute(nodes.get(10));
		// test 10 -> 6
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(6));
		System.out.println("Test(4) 10 -> 6 | " + path);
		for (Vertex vertex : path) {
			System.out.println(vertex);
		}
		assertNotNull(path);
		assertTrue(path.size() == resultSize);
		assertEquals(resultVertex, path.get(resultSize - 1));
	}

}