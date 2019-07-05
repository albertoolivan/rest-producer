package org.adidas.code.challange.rest.producer.graph;

import java.util.LinkedList;

public class IntineraryDTO {

	private LinkedList<Vertex> path = new LinkedList<Vertex>();
	private int sumPathWeight;

	public IntineraryDTO() {
	}

	public LinkedList<Vertex> getPath() {
		return path;
	}

	public void setPath(LinkedList<Vertex> path) {
		this.path = path;
	}

	public int getSumPathWeight() {
		return sumPathWeight;
	}

	public void setSumPathWeight(int sumPathWeight) {
		this.sumPathWeight = sumPathWeight;
	}

	@Override
	public String toString() {
		return "IntineraryDTO [path=" + path + ", sumPathWeight=" + sumPathWeight + "]";
	}

}
