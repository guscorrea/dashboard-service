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

import com.dt.dashboardservice.model.chokevalve.CustomMeasure;
import com.dt.dashboardservice.model.chokevalve.Pressure;
import com.dt.dashboardservice.model.chokevalve.Temperature;
import com.dt.dashboardservice.model.tubing.Tubing;
import com.dt.dashboardservice.model.tubing.TubingRequest;

@Component
public class TubingClient {

	private static final String TUBING_PATH = "/tubing";

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public TubingClient(@Value("${tubing.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Tubing> getAllTubings() {
		try {
			ResponseEntity<Tubing[]> response = restTemplate.exchange(serviceRootUrl + TUBING_PATH, HttpMethod.GET, createHeaders(),
					Tubing[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public Tubing postTubing(TubingRequest tubingRequest) {
		ResponseEntity<Tubing> response = restTemplate.postForEntity(serviceRootUrl + TUBING_PATH, tubingRequest, Tubing.class);
		return response.getBody();
	}

	public void putTubing(TubingRequest tubingRequest, UUID tubingId) {
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + TUBING_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, tubingRequest);
	}

	public void deleteTubing(UUID tubingId) {
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + TUBING_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	public List<Pressure> getAllPressuresById(UUID componentId) {
		String pathVariable = "/pressure/" + componentId.toString();
		ResponseEntity<Pressure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				Pressure[].class);
		return Arrays.asList(response.getBody());
	}

	public List<Temperature> getAllTemperaturesById(UUID componentId) {
		String pathVariable = "/temperature/" + componentId.toString();
		ResponseEntity<Temperature[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				Temperature[].class);
		return Arrays.asList(response.getBody());
	}

	public List<CustomMeasure> getAllCustomMeasuresById(UUID componentId) {
		String pathVariable = "/measure/" + componentId.toString();
		ResponseEntity<CustomMeasure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				CustomMeasure[].class);
		return Arrays.asList(response.getBody());
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
