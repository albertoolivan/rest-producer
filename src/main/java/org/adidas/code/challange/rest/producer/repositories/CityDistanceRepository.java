package org.adidas.code.challange.rest.producer.repositories;


import java.util.List;

import org.adidas.code.challange.rest.producer.entities.CityDistance;
import org.springframework.data.repository.CrudRepository;



public interface CityDistanceRepository extends CrudRepository<CityDistance, String> {
	
	List<CityDistance> findByCityOriginId(String cityOriginId);
}