package org.adidas.code.challange.rest.producer.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Dijkstra algorithm, from a graph of vertexes and edges, find short path. 
 * 
 * https://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 * 
 * @author alberto.olivan
 *
 */
public class DijkstraAlgorithm {
	
	private static Logger logger = LoggerFactory.getLogger(DijkstraAlgorithm.class);

	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;

	public DijkstraAlgorithm(Graph graph) {
		// create a copy of the array so that we can operate on this array
		this.nodes = new ArrayList<>(graph.getVertexes());
		this.edges = new ArrayList<>(graph.getEdges());
	}

	public void execute(Vertex source) {
		settledNodes = new HashSet<>();
		unSettledNodes = new HashSet<>();
		distance = new HashMap<>();
		predecessors = new HashMap<>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			logger.debug("execute(" + node + ") size " + unSettledNodes.size());
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		logger.info("findMinimalDistances(" + node + ") - " + adjacentNodes);
		for (Vertex target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				logger.debug("findMinimalDistances put (" + target + ")");
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				logger.debug("getDistance " + node + " - " + target + " = " + edge.getWeight());
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		logger.info("getNeighbors " + node + " = " + neighbors);
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		logger.info("getMinimum " + vertexes + " = " + minimum);
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/**
	 * This method returns the path from the source to the selected target and NULL
	 * if no path exists
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<>();
		Vertex step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * Sun all distance kilometers of a LinkedList<Vertex>
	 * 
	 * @param path
	 * @return int
	 */
	public int sumPathWeight(LinkedList<Vertex> path) {
		int sumWeight = 0;
		for (int i = 0; i<path.size()-1; i++) {
			int weight = 0;
			Vertex current = path.get(i);
			Vertex next = path.get(i+1);
			logger.debug("sumPathWeight i:"+i+" current " + current + " next " + next + " sum " + sumWeight);
			if (next != null) {
				for (Edge edge : edges) {
					if (edge.getSource().equals(current) && edge.getDestination().equals(next)) {
						logger.debug("Edge used: " + edge.toString());
						weight = edge.getWeight();
					}
				}
			} else {
				logger.warn("step null!");
			}
			
			sumWeight = sumWeight + weight;
		}
		logger.info("sumPathWeight result: " + sumWeight);
		
		return sumWeight;
	}
	
	public LinkedList<Edge> getEdgeFromPath(LinkedList<Vertex> path) {
		LinkedList<Edge> edgePath = new LinkedList<>();
		for (int i = 0; i<path.size()-1; i++) {
			Vertex current = path.get(i);
			Vertex next = path.get(i+1);
			if (next != null) {
				for (Edge edge : edges) {
					if (edge.getSource().equals(current) && edge.getDestination().equals(next)) {
						logger.debug("Edge used: " + edge.toString());
						edgePath.add(edge);
					}
				}
			} else {
				logger.warn("step null!");
			}
		}
		logger.info("edgePath result: " + edgePath);
		
		return edgePath;
	}

}