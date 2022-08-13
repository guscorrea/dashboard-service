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
import com.dt.dashboardservice.model.well.Well;
import com.dt.dashboardservice.model.well.WellRequest;
import com.dt.dashboardservice.utils.HttpEntityCreator;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WellOrchestratorClient {

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public WellOrchestratorClient(@Value("${well.orchestrator.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public List<Well> getAllWells() {
		try {
			ResponseEntity<Well[]> response = restTemplate.exchange(serviceRootUrl + ComponentPathConstants.WELL_PATH, HttpMethod.GET,
					HttpEntityCreator.createHeaders(), Well[].class);
			return Arrays.asList(response.getBody());
		}
		catch (RestClientException e) {
			return new ArrayList<>();
		}
	}

	public Well getWell(UUID wellId) {
		log.info("Sending a retrieve well request with id {}", wellId);
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.WELL_PATH).path(pathVariable).toUriString();
		ResponseEntity<Well> response = restTemplate.exchange(uri, HttpMethod.GET, HttpEntityCreator.createHeaders(), Well.class);
		return response.getBody();
	}

	public Well postWell(WellRequest wellRequest) {
		log.info("Sending a create well request with name {}", wellRequest.getName());
		ResponseEntity<Well> response = restTemplate.postForEntity(serviceRootUrl + ComponentPathConstants.WELL_PATH, wellRequest, Well.class);
		return response.getBody();
	}

	public void putWell(WellRequest wellRequest, UUID wellId) {
		log.info("Sending a update well request with name: {}", wellRequest.getName());
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.WELL_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, wellRequest);
	}

	public void deleteWell(UUID wellId) {
		log.info("Sending a delete well request with id: {}", wellId);
		String pathVariable = "/" + wellId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + ComponentPathConstants.WELL_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

}
