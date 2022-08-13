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
import com.dt.dashboardservice.model.chokevalve.CustomMeasure;
import com.dt.dashboardservice.model.chokevalve.Pressure;
import com.dt.dashboardservice.model.chokevalve.Temperature;
import com.dt.dashboardservice.model.tubing.Tubing;
import com.dt.dashboardservice.model.tubing.TubingRequest;
import com.dt.dashboardservice.utils.HttpEntityCreator;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TubingClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public TubingClient(@Value("${tubing.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Tubing> getAllTubings() {
		try {
			ResponseEntity<Tubing[]> response = restTemplate.exchange(serviceRootUrl + ComponentPathConstants.TUBING_PATH, HttpMethod.GET,
					HttpEntityCreator.createHeaders(), Tubing[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public Tubing getTubing(UUID tubingId) {
		log.info("Sending a retrieve tubing request with id {}", tubingId);
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.TUBING_PATH).path(pathVariable).toUriString();
		ResponseEntity<Tubing> response = restTemplate.exchange(uri, HttpMethod.GET, HttpEntityCreator.createHeaders(), Tubing.class);
		return response.getBody();
	}

	public Tubing postTubing(TubingRequest tubingRequest) {
		log.info("Sending a create tubing request with name {}", tubingRequest.getName());
		ResponseEntity<Tubing> response = restTemplate.postForEntity(serviceRootUrl + ComponentPathConstants.TUBING_PATH, tubingRequest,
				Tubing.class);
		return response.getBody();
	}

	public void putTubing(TubingRequest tubingRequest, UUID tubingId) {
		log.info("Sending a update tubing request with name: {}", tubingRequest.getName());
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.TUBING_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, tubingRequest);
	}

	public void deleteTubing(UUID tubingId) {
		log.info("Sending a delete tubing request with id: {}", tubingId);
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.TUBING_PATH).path(pathVariable).toUriString();
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

	public List<CustomMeasure> getAllCustomMeasuresById(UUID componentId) {
		String pathVariable = ComponentPathConstants.CUSTOM_MEASURE_PATH + componentId.toString();
		ResponseEntity<CustomMeasure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(),
				CustomMeasure[].class);
		return Arrays.asList(response.getBody());
	}

}
