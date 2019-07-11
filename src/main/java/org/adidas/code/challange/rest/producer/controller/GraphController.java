package org.adidas.code.challange.rest.producer.controller;

import org.adidas.code.challange.rest.dto.ExceptionResponseDTO;
import org.adidas.code.challange.rest.producer.service.GraphService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class GraphController {
	
	private static Logger logger = LoggerFactory.getLogger(GraphController.class);
	
	@Autowired
	GraphService graphService;
	
	/**
	 * GET /graph/reset
	 * 
	 * Example:
	 * http://localhost:8098/graph/reset
	 * 
	 * Reset cached graph and reload to get database changes
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/graph/reset", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> grapReset() {
		try {
			logger.info("Rest resetGraph()");

			String result = graphService.resetGraph();

			logger.info("Rest resetGraph() Result {}", result);

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
