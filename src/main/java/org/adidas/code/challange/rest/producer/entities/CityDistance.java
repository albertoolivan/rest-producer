package org.adidas.code.challange.rest.producer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ch_distance")
public class CityDistance {

	private String id;

	private City cityOrigin = null;

	private City cityDestination = null;

	private int distanceKm;

	public CityDistance() {
	}

	public CityDistance(String id, City cityOrigin, City cityDestination, int distanceKm) {
		this.id = id;
		this.cityOrigin = cityOrigin;
		this.cityDestination = cityDestination;
		this.distanceKm = distanceKm;
	}

	@Id
	@Column(name = "id", nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_origin_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK1_CityOrigin"))
	public City getCityOrigin() {
		return cityOrigin;
	}

	public void setCityOrigin(City cityOrigin) {
		this.cityOrigin = cityOrigin;
	}

	@ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "city_destination_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "FK1_CityDestination"))
	public City getCityDestination() {
		return cityDestination;
	}

	public void setCityDestination(City cityDestination) {
		this.cityDestination = cityDestination;
	}

	@Column(name = "distance_km", nullable = false)
	public int getDistanceKm() {
		return distanceKm;
	}

	public void setDistanceKm(int distanceKm) {
		this.distanceKm = distanceKm;
	}

	@Override
	public String toString() {
		return "Distance [cityOrigin=" + cityOrigin + ", cityDestination=" + cityDestination + ", distanceKm="
				+ distanceKm + "]";
	}

}
