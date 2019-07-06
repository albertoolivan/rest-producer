package org.adidas.code.challange.rest.producer.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ch_city")
public class City {

	// city id: MAD
	private String id;
	// city name: Madrid
	private String name;
	// list of paths reachable from city
	private List<CityDistance> distanceList = new ArrayList<>();

	public City() {

	}
	
	public City(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "city_id", nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_origen_id")
	public List<CityDistance> getDistanceList() {
		return distanceList;
	}

	public void setDistanceList(List<CityDistance> distanceList) {
		this.distanceList = distanceList;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + "]";
	}

}
