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
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TubingClient {

	private static final String TUBING_PATH = "/v1/tubing";

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

	public Tubing getTubing(UUID tubingId) {
		log.info("Sending a retrieve tubing request with id {}", tubingId);
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + TUBING_PATH).path(pathVariable).toUriString();
		ResponseEntity<Tubing> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Tubing.class);
		return response.getBody();
	}

	public Tubing postTubing(TubingRequest tubingRequest) {
		log.info("Sending a create tubing request with name {}", tubingRequest.getName());
		ResponseEntity<Tubing> response = restTemplate.postForEntity(serviceRootUrl + TUBING_PATH, tubingRequest, Tubing.class);
		return response.getBody();
	}

	public void putTubing(TubingRequest tubingRequest, UUID tubingId) {
		log.info("Sending a update tubing request with name: {}", tubingRequest.getName());
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + TUBING_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, tubingRequest);
	}

	public void deleteTubing(UUID tubingId) {
		log.info("Sending a delete tubing request with id: {}", tubingId);
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + TUBING_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	public List<Pressure> getAllPressuresById(UUID componentId) {
		String pathVariable = "/v1/pressure/" + componentId.toString();
		ResponseEntity<Pressure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				Pressure[].class);
		return Arrays.asList(response.getBody());
	}

	public List<Temperature> getAllTemperaturesById(UUID componentId) {
		String pathVariable = "/v1/temperature/" + componentId.toString();
		ResponseEntity<Temperature[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				Temperature[].class);
		return Arrays.asList(response.getBody());
	}

	public List<CustomMeasure> getAllCustomMeasuresById(UUID componentId) {
		String pathVariable = "/v1/measure/" + componentId.toString();
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
