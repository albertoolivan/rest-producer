package org.adidas.code.challange.rest.producer.controller;

import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.ExceptionResponseDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

	private static Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	/**
	 * GET /city/info/{id}
	 * 
	 * Example: http://localhost:8098/city/info/MAD
	 * 
	 * Get City info
	 * 
	 * @param id
	 * @return City
	 */
	@RequestMapping(value = "/city/info/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCityInfo(@PathVariable String id) {
		try {
			logger.info("Rest getCityInfo() called with id {}", id);

			CityDTO cityDTO = cityService.getCity(id);

			logger.info("Rest getCityInfo() Return {}", cityDTO);

			if (cityDTO != null) {
				return ResponseEntity.ok().body(cityDTO);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * GET /city/itinerary_short
	 * 
	 * Example:
	 * http://localhost:8098/city/itinerary_short?cityOriginId=MAD&cityDestinationId=BER
	 * 
	 * Get IntineraryDTO info
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @return IntineraryDTO
	 */
	@RequestMapping(value = "/city/itinerary_short", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> initneraryShort(@RequestParam(value = "cityOriginId", required = true) String cityOriginId,
			@RequestParam(value = "cityDestinationId", required = true) String cityDestinationId) {
		try {
			logger.info("Rest getCityInfo() called with cityOriginId {} cityOriginId {}", cityOriginId, cityOriginId);

			IntineraryDTO result = cityService.getItineraryShort(cityOriginId, cityDestinationId);

			logger.info("Rest getCityInfo() Return {}", result);

			if (result != null) {
				return ResponseEntity.ok().body(result);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}