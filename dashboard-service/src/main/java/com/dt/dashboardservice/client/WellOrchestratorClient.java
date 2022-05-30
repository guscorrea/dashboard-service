package com.dt.dashboardservice.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dt.dashboardservice.model.Well;

@Component
public class WellOrchestratorClient {

	private static final String GET_DOCUMENT_ENDPOINT = "/well-orchestrator/well";

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public WellOrchestratorClient(@Value("${service.root.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Well> getAllWells() {
		String uri = serviceRootUrl + GET_DOCUMENT_ENDPOINT;
		ResponseEntity<Well[]> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Well[].class);
		return Arrays.asList(response.getBody());
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
