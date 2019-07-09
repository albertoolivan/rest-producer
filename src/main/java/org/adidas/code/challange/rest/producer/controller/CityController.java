package org.adidas.code.challange.rest.producer.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.adidas.code.challange.rest.dto.CityDTO;
import org.adidas.code.challange.rest.dto.ExceptionResponseDTO;
import org.adidas.code.challange.rest.dto.IntineraryDTO;
import org.adidas.code.challange.rest.producer.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
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
	 * @return CityDTO
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
	 * GET /city/all
	 * 
	 * Example: http://localhost:8098/city/all
	 * 
	 * Get all Cities info
	 * 
	 * @param id
	 * @return List<CityDTO>
	 */
	@RequestMapping(value = "/city/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCityAll() {
		try {
			logger.info("Rest getCityAll() called");

			List<CityDTO> cityDTOList = cityService.getCityAll();

			logger.info("Rest getCityAll() Return {}", cityDTOList.size());

			return ResponseEntity.ok().body(cityDTOList);

		} catch (Exception e) {
			return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * GET /city/itinerary-short
	 * 
	 * Example:
	 * http://localhost:8098/city/itinerary-short?cityOriginId=MAD&cityDestinationId=BER&departureTime=2019-07-10T01:30:00.000Z
	 * 
	 * Get IntineraryDTO info from calculating short distance path
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param departureTime
	 * @return IntineraryDTO
	 */
	@RequestMapping(value = "/city/itinerary-short", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> itineraryShort(@RequestParam(value = "cityOriginId", required = true) String cityOriginId,
			@RequestParam(value = "cityDestinationId", required = true) String cityDestinationId,
			@RequestParam(value = "departureTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime) {
		try {
			logger.info("Rest itineraryShort() called with cityOriginId {} cityOriginId {} departureTime {}", cityOriginId,
					cityOriginId, departureTime);

			IntineraryDTO result = cityService.getItineraryShortDistance(cityOriginId, cityDestinationId, departureTime);

			logger.info("Rest itineraryShort() Return {}", result);

			if (result != null) {
				return ResponseEntity.ok().body(result);
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * GET /city/itinerary-less
	 * 
	 * Example:
	 * http://localhost:8098/city/itinerary-less?cityOriginId=MAD&cityDestinationId=BER&departureTime=2019-07-10T01:30:00.000Z
	 * 
	 * Get IntineraryDTO info from calculating less steps path
	 * 
	 * @param cityOriginId
	 * @param cityDestinationId
	 * @param departureTime
	 * @return IntineraryDTO
	 */
	@RequestMapping(value = "/city/itinerary-less", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> itineraryLess(@RequestParam(value = "cityOriginId", required = true) String cityOriginId,
			@RequestParam(value = "cityDestinationId", required = true) String cityDestinationId,
			@RequestParam(value = "departureTime", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime) {
		try {
			logger.info("Rest itineraryLess() called with cityOriginId {} cityOriginId {} departureTime {}", cityOriginId, cityOriginId, departureTime);

			IntineraryDTO result = cityService.getItineraryLessSteps(cityOriginId, cityDestinationId, departureTime);

			logger.info("Rest itineraryLess() Return {}", result);

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