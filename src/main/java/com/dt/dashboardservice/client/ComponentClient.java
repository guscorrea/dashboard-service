package com.dt.dashboardservice.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dt.dashboardservice.constants.ComponentPathConstants;
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
		restTemplate.postForEntity(serviceRootUrl + String.format(ComponentPathConstants.ADD_COMPONENT_PATH, wellId.toString()), componentRequest,
				Well.class);
	}

	public void removeComponent(UUID wellId, UUID componentId) {
		log.info("Sending a remove-component request for well with id: {}. Component id: {}", wellId, componentId);
		restTemplate.delete(serviceRootUrl + String.format(ComponentPathConstants.REMOVE_COMPONENT_PATH, wellId.toString(), componentId.toString()));
	}

}
