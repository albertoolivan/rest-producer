package org.adidas.code.challange.rest.producer.repositories;


import org.adidas.code.challange.rest.producer.entities.City;
import org.springframework.data.repository.CrudRepository;



public interface CityRepository extends CrudRepository<City, String> {
}