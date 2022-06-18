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

import com.dt.dashboardservice.model.chokevalve.ChokeValve;
import com.dt.dashboardservice.model.chokevalve.ChokeValveRequest;

@Component
public class ChokeValveClient {

	private static final String CHOKE_VALVE_PATH = "/choke-valve";

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public ChokeValveClient(@Value("${choke.valve.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<ChokeValve> getAllChokeValves() {
		try {
			ResponseEntity<ChokeValve[]> response = restTemplate.exchange(serviceRootUrl + CHOKE_VALVE_PATH, HttpMethod.GET, createHeaders(),
					ChokeValve[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public ChokeValve postChokeValve(ChokeValveRequest chokeValveRequest) {
		ResponseEntity<ChokeValve> response = restTemplate.postForEntity(serviceRootUrl + CHOKE_VALVE_PATH, chokeValveRequest, ChokeValve.class);
		return response.getBody();
	}

	public void putChokeValve(ChokeValveRequest chokeValveRequest, UUID chokeValveId) {
		String pathVariable = "/" + chokeValveId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + CHOKE_VALVE_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, chokeValveRequest);
	}

	public void deleteChokeValve(UUID chokeValveId) {
		String pathVariable = "/" + chokeValveId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + CHOKE_VALVE_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
