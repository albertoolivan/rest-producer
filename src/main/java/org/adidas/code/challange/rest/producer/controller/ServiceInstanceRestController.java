package org.adidas.code.challange.rest.producer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInstanceRestController {
	
	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * GET /service-instances/{applicationName}
	 * 
	 * Example: http://localhost:8082 /service-instances/rest-producer
	 * 
	 * Get ServiStringceInstance from eureka server
	 * 
	 * @param applicationName
	 * @return List<ServiStringceInstance>
	 */
	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

}
