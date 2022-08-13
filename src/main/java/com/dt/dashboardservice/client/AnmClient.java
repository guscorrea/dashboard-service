package com.dt.dashboardservice.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dt.dashboardservice.constants.ComponentPathConstants;
import com.dt.dashboardservice.model.anm.Anm;
import com.dt.dashboardservice.model.anm.AnmRequest;
import com.dt.dashboardservice.model.chokevalve.CustomMeasure;
import com.dt.dashboardservice.model.chokevalve.Pressure;
import com.dt.dashboardservice.model.chokevalve.Temperature;
import com.dt.dashboardservice.utils.HttpEntityCreator;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AnmClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public AnmClient(@Value("${anm.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Anm> getAllAnms() {
		try {
			ResponseEntity<Anm[]> response = restTemplate.exchange(serviceRootUrl + ComponentPathConstants.ANM_PATH, HttpMethod.GET,
					HttpEntityCreator.createHeaders(), Anm[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public Anm postAnm(AnmRequest anmRequest) {
		log.info("Sending a create ANM request with name {}", anmRequest.getName());
		ResponseEntity<Anm> response = restTemplate.postForEntity(serviceRootUrl + ComponentPathConstants.ANM_PATH, anmRequest, Anm.class);
		return response.getBody();
	}

	public void putAnm(AnmRequest anmRequest, UUID anmId) {
		log.info("Sending a update ANM request with name: {}", anmRequest.getName());
		String pathVariable = "/" + anmId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.ANM_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, anmRequest);
	}

	public void deleteAnm(UUID anmId) {
		log.info("Sending a delete ANM request with id: {}", anmId);
		String pathVariable = "/" + anmId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.ANM_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	public List<Pressure> getAllPressuresById(UUID componentId) {
		String pathVariable = ComponentPathConstants.PRESSURE_PATH + componentId.toString();
		ResponseEntity<Pressure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET, HttpEntityCreator.createHeaders(),
				Pressure[].class);
		return Arrays.asList(response.getBody());
	}

	public List<Temperature> getAllTemperaturesById(UUID componentId) {
		String pathVariable = ComponentPathConstants.TEMPERATURE_PATH + componentId.toString();
		ResponseEntity<Temperature[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET,
				HttpEntityCreator.createHeaders(), Temperature[].class);
		return Arrays.asList(response.getBody());
	}

	public List<CustomMeasure> getAllCustomMeasuresById(UUID componentId) {
		String pathVariable = ComponentPathConstants.CUSTOM_MEASURE_PATH + componentId.toString();
		ResponseEntity<CustomMeasure[]> response = restTemplate.exchange(serviceRootUrl + pathVariable, HttpMethod.GET,
				HttpEntityCreator.createHeaders(), CustomMeasure[].class);
		return Arrays.asList(response.getBody());
	}

}
