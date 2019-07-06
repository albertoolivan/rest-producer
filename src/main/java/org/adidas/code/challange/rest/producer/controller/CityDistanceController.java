package org.adidas.code.challange.rest.producer.controller;

import java.util.List;

import org.adidas.code.challange.rest.dto.CityDistanceDTO;
import org.adidas.code.challange.rest.dto.ExceptionResponseDTO;
import org.adidas.code.challange.rest.producer.service.CityDistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityDistanceController {

	 private static Logger logger = LoggerFactory.getLogger(CityController.class);

		@Autowired
		private CityDistanceService cityDistanceService;

		/**
		 * GET /city-distance/{id}
		 * 
		 * Example:	http://localhost:8098/city-distance?cityOriginId=MAD
		 * 
		 * Get CityDistance list with cityOriginId
		 * 
		 * @param cityOriginId
		 * @return List<CityDistance>
		 */
		@RequestMapping(value = "/city-distance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> getCityDistanceByOrigin(@RequestParam(value = "cityOriginId", required = true) String cityOriginId) {
			try {
				logger.info("Rest getCitydistanceInfo() called with id {}", cityOriginId);

				List<CityDistanceDTO> cityDistanceDTOList = cityDistanceService.getCityDistanceListByOriginId(cityOriginId);

				logger.info("Rest getCityInfo() Return {}", cityDistanceDTOList);
				
				if (cityDistanceDTOList != null) {
					return ResponseEntity.ok().body(cityDistanceDTOList);
				} else {
					return ResponseEntity.notFound().build();
				}
				
			} catch (Exception e) {
				return new ResponseEntity<>(new ExceptionResponseDTO(e), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	 
}
