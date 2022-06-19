package com.dt.dashboardservice.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ComponentClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public ComponentClient(@Value("${well.orchestrator.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public void removeComponent(UUID wellId, UUID componentId) {
		String path = "/well-orchestrator/remove-component/well/%s/component/%s";
		restTemplate.delete(serviceRootUrl + String.format(path, wellId.toString(), componentId.toString()));
	}

}
