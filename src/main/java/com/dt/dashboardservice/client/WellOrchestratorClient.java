package com.dt.dashboardservice.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dt.dashboardservice.model.well.Well;
import com.dt.dashboardservice.model.well.WellRequest;

@Component
public class WellOrchestratorClient {

	private static final String WELL_PATH = "/v1/well";

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public WellOrchestratorClient(@Value("${well.orchestrator.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Well> getAllWells() {
		try {
			ResponseEntity<Well[]> response = restTemplate.exchange(serviceRootUrl + WELL_PATH, HttpMethod.GET, createHeaders(), Well[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public Well getWell(UUID wellId) {
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + WELL_PATH).path(pathVariable).toUriString();
		ResponseEntity<Well> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Well.class);
		return response.getBody();
	}

	public Well postWell(WellRequest wellRequest) {
		ResponseEntity<Well> response = restTemplate.postForEntity(serviceRootUrl + WELL_PATH, wellRequest, Well.class);
		return response.getBody();
	}

	public void putWell(WellRequest wellRequest, UUID wellId) {
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + WELL_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, wellRequest);
	}

	public void deleteWell(UUID wellId) {
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + WELL_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
