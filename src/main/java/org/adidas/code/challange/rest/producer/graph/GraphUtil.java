package org.adidas.code.challange.rest.producer.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to test DijkstraAlgorithm
 * 
 * @author Alberto
 *
 */
public class GraphUtil {
	
	public static Graph getGraphReal() {
		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();

		Vertex cityMAD = new Vertex("MAD", "Madrid");
		nodes.add(cityMAD);
		Vertex cityBCN = new Vertex("BCN", "Barcelona");
		nodes.add(cityBCN);
		Vertex cityPAR = new Vertex("PAR", "Paris");
		nodes.add(cityPAR);
		Vertex cityZGZ = new Vertex("ZGZ", "Zaragoza");
		nodes.add(cityZGZ);
		Vertex cityBIL = new Vertex("BIL", "Bilbao");
		nodes.add(cityBIL);
		Vertex cityVAL = new Vertex("VAL", "Valencia");
		nodes.add(cityVAL);
		Vertex citySEV = new Vertex("SEV", "Sevilla");
		nodes.add(citySEV);
		Vertex cityLIS = new Vertex("LIS", "Lisboa");
		nodes.add(cityLIS);
		
		Edge lanePAR1 = new Edge("lanePAR1", cityPAR, cityBCN, 850);
		edges.add(lanePAR1);
		Edge lanePAR2 = new Edge("lanePAR2", cityPAR, cityBIL, 1400);
		edges.add(lanePAR2);
		
		Edge laneBCN1 = new Edge("laneBCN1", cityBCN, cityPAR, 850);
		edges.add(laneBCN1);
		Edge laneBCN2 = new Edge("laneBCN2", cityBCN, cityZGZ, 250);
		edges.add(laneBCN2);
		Edge laneBCN3 = new Edge("laneBCN3", cityBCN, cityVAL, 220);
		edges.add(laneBCN3);
		
		Edge laneZGZ1 = new Edge("laneZGZ1", cityZGZ, cityBCN, 250);
		edges.add(laneZGZ1);
		Edge laneZGZ2 = new Edge("laneZGZ2", cityZGZ, cityBIL, 290);
		edges.add(laneZGZ2);
		Edge laneZGZ3 = new Edge("laneZGZ3", cityZGZ, cityVAL, 300);
		edges.add(laneZGZ3);
		
		Edge laneBIL1 = new Edge("laneBIL1", cityBIL, cityZGZ, 290);
		edges.add(laneBIL1);
		Edge laneBIL2 = new Edge("laneBIL2", cityBIL, citySEV, 800);
		edges.add(laneBIL2);
		Edge laneBIL3 = new Edge("laneBIL3", cityBIL, cityPAR, 1400);
		edges.add(laneBIL3);
		
		Edge laneVAL1 = new Edge("laneVAL1", cityVAL, cityZGZ, 300);
		edges.add(laneVAL1);
		Edge laneVAL2 = new Edge("laneVAL2", cityVAL, citySEV, 600);
		edges.add(laneVAL2);
		Edge laneVAL3 = new Edge("laneVAL3", cityVAL, cityBCN, 220);
		edges.add(laneVAL3);
		
		Edge laneMAD1 = new Edge("laneMAD1", cityMAD, citySEV, 400);
		edges.add(laneMAD1);
		Edge laneMAD2 = new Edge("laneMAD2", cityMAD, cityLIS, 700);
		edges.add(laneMAD2);
		
		Edge laneLIS1 = new Edge("laneLIS1", cityLIS, citySEV, 360);
		edges.add(laneLIS1);
		Edge laneLIS2 = new Edge("laneLIS2", cityLIS, cityMAD, 700);
		edges.add(laneLIS2);
		
		Edge laneSEV1 = new Edge("laneSEV1", citySEV, cityMAD, 400);
		edges.add(laneSEV1);
		Edge laneSEV2 = new Edge("laneVAL2", citySEV, cityVAL, 600);
		edges.add(laneSEV2);
		Edge laneSEV3 = new Edge("laneVAL3", citySEV, cityLIS, 360);
		edges.add(laneSEV3);
		

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(nodes, edges);
		return graph;
	}

	public static Graph getGraphFull() {

		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();
		
		for (int i = 0; i < 11; i++) {
			Vertex location = new Vertex("Node_" + i, "Node_" + i);
			nodes.add(location);
		}

		addLane(nodes, edges, "Edge_0", 0, 1, 85);
		addLane(nodes, edges, "Edge_1", 0, 2, 217);
		addLane(nodes, edges, "Edge_2", 0, 4, 173);
		addLane(nodes, edges, "Edge_3", 2, 6, 186);
		addLane(nodes, edges, "Edge_4", 2, 7, 103);
		addLane(nodes, edges, "Edge_5", 3, 7, 183);
		addLane(nodes, edges, "Edge_6", 5, 8, 250);
		addLane(nodes, edges, "Edge_7", 8, 9, 84);
		addLane(nodes, edges, "Edge_8", 7, 9, 167);
		addLane(nodes, edges, "Edge_9", 4, 9, 502);
		addLane(nodes, edges, "Edge_10", 9, 10, 40);
		addLane(nodes, edges, "Edge_11", 1, 10, 600);
		
		// both ways, because is directional
		addLane(nodes, edges, "Edge_00", 1, 0, 85);
		addLane(nodes, edges, "Edge_01", 2, 0, 217);
		addLane(nodes, edges, "Edge_02", 4, 0, 173);
		addLane(nodes, edges, "Edge_03", 6, 2, 186);
		addLane(nodes, edges, "Edge_04", 7, 2, 103);
		addLane(nodes, edges, "Edge_05", 7, 3, 183);
		addLane(nodes, edges, "Edge_06", 8, 5, 250);
		addLane(nodes, edges, "Edge_07", 9, 8, 84);
		addLane(nodes, edges, "Edge_08", 9, 7, 167);
		addLane(nodes, edges, "Edge_09", 9, 4, 502);
		addLane(nodes, edges, "Edge_010", 10, 9, 40);
		addLane(nodes, edges, "Edge_011", 10, 1, 600);

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(nodes, edges);
		return graph;
	}

	private static void addLane(List<Vertex> nodes, List<Edge> edges, String laneId, int sourceLocNo, int destLocNo,
			int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);
	}
	
}
