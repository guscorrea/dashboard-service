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
import com.dt.dashboardservice.model.chokevalve.CustomMeasure;
import com.dt.dashboardservice.model.chokevalve.Flow;
import com.dt.dashboardservice.model.chokevalve.Pressure;
import com.dt.dashboardservice.model.chokevalve.Temperature;

@Component
public class ChokeValveClient {

	private static final String CHOKE_VALVE_PATH = "/v1/choke-valve";

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

	public List<Flow> getAllFlowsById(UUID componentId) {
		String pathVariable = "/v1/flow/" + componentId.toString();
		ResponseEntity<Flow[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, createHeaders(),
				Flow[].class);
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
