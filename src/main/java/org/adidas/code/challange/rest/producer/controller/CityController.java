package org.adidas.code.challange.rest.producer.controller;

import org.adidas.code.challange.rest.producer.entities.City;
import org.adidas.code.challange.rest.producer.exception.ExceptionResponseDTO;
import org.adidas.code.challange.rest.producer.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

	private static Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	/**
	 * GET /delforcustomer/getreference_crosspt?numArticulo=111
	 * 
	 * Example:
	 * http://localhost:8092/delforcustomer/getreference_crosspt?numArticulo=760971095BQ
	 * 
	 * @param numArticulo
	 * @return String
	 */
	@RequestMapping(value = "/city/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCityInfo(@PathVariable String id) {
		try {
			logger.info("Rest getCityInfo() called with id {}", id);

			City city = cityService.getCity(id);

			logger.info("Rest getCityInfo() Return {}", city);
			
			if (city != null) {
				return ResponseEntity.ok().body(city);
			} else {
				return ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}