package com.dt.dashboardservice.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dt.dashboardservice.model.well.ComponentRequest;
import com.dt.dashboardservice.model.well.Well;

@Component
public class ComponentClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public ComponentClient(@Value("${well.orchestrator.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public void addComponent(UUID wellId, ComponentRequest componentRequest) {
		String path = "/v1/add-component/%s";
		ResponseEntity<Well> response = restTemplate.postForEntity(serviceRootUrl + String.format(path, wellId.toString()), componentRequest,
				Well.class);
	}

	public void removeComponent(UUID wellId, UUID componentId) {
		String path = "/v1/remove-component/well/%s/component/%s";
		restTemplate.delete(serviceRootUrl + String.format(path, wellId.toString(), componentId.toString()));
	}

}
