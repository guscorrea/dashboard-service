package com.dt.dashboardservice.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dt.dashboardservice.model.well.ComponentRequest;
import com.dt.dashboardservice.model.well.Well;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ComponentClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public ComponentClient(@Value("${well.orchestrator.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public void addComponent(UUID wellId, ComponentRequest componentRequest) {
		log.info("Sending a add-component request for well with id: {}. Component id: {}", wellId, componentRequest.getComponentId());
		String path = "/v1/add-component/%s";
		restTemplate.postForEntity(serviceRootUrl + String.format(path, wellId.toString()), componentRequest, Well.class);
	}

	public void removeComponent(UUID wellId, UUID componentId) {
		log.info("Sending a remove-component request for well with id: {}. Component id: {}", wellId, componentId);
		String path = "/v1/remove-component/well/%s/component/%s";
		restTemplate.delete(serviceRootUrl + String.format(path, wellId.toString(), componentId.toString()));
	}

}
