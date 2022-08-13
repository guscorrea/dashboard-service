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

import com.dt.dashboardservice.constants.ComponentPathConstants;
import com.dt.dashboardservice.model.chokevalve.ChokeValve;
import com.dt.dashboardservice.model.chokevalve.ChokeValveRequest;
import com.dt.dashboardservice.model.chokevalve.CustomMeasure;
import com.dt.dashboardservice.model.chokevalve.Flow;
import com.dt.dashboardservice.model.chokevalve.Pressure;
import com.dt.dashboardservice.model.chokevalve.Temperature;
import com.dt.dashboardservice.utils.HttpEntityCreator;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ChokeValveClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public ChokeValveClient(@Value("${choke.valve.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<ChokeValve> getAllChokeValves() {
		try {
			ResponseEntity<ChokeValve[]> response = restTemplate.exchange(serviceRootUrl + ComponentPathConstants.CHOKE_VALVE_PATH, HttpMethod.GET,
					HttpEntityCreator.createHeaders(), ChokeValve[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public ChokeValve postChokeValve(ChokeValveRequest chokeValveRequest) {
		log.info("Sending a create Choke Valve request with name {}", chokeValveRequest.getName());
		ResponseEntity<ChokeValve> response = restTemplate.postForEntity(serviceRootUrl + ComponentPathConstants.CHOKE_VALVE_PATH, chokeValveRequest,
				ChokeValve.class);
		return response.getBody();
	}

	public void putChokeValve(ChokeValveRequest chokeValveRequest, UUID chokeValveId) {
		log.info("Sending a update Choke Valve request with name: {}", chokeValveRequest.getName());
		String pathVariable = "/" + chokeValveId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.CHOKE_VALVE_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, chokeValveRequest);
	}

	public void deleteChokeValve(UUID chokeValveId) {
		log.info("Sending a delete Choke Valve request with id: {}", chokeValveId);
		String pathVariable = "/" + chokeValveId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.CHOKE_VALVE_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	public List<Pressure> getAllPressuresById(UUID componentId) {
		String pathVariable = ComponentPathConstants.PRESSURE_PATH + componentId.toString();
		ResponseEntity<Pressure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(), Pressure[].class);
		return Arrays.asList(response.getBody());
	}

	public List<Temperature> getAllTemperaturesById(UUID componentId) {
		String pathVariable = ComponentPathConstants.TEMPERATURE_PATH + componentId.toString();
		ResponseEntity<Temperature[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(),
				Temperature[].class);
		return Arrays.asList(response.getBody());
	}

	public List<Flow> getAllFlowsById(UUID componentId) {
		String pathVariable = ComponentPathConstants.FLOW_PATH + componentId.toString();
		ResponseEntity<Flow[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(), Flow[].class);
		return Arrays.asList(response.getBody());
	}

	public List<CustomMeasure> getAllCustomMeasuresById(UUID componentId) {
		String pathVariable = ComponentPathConstants.CUSTOM_MEASURE_PATH + componentId.toString();
		ResponseEntity<CustomMeasure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(),
				CustomMeasure[].class);
		return Arrays.asList(response.getBody());
	}

}
