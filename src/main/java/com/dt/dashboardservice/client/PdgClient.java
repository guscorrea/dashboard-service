package com.dt.dashboardservice.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dt.dashboardservice.model.tubing.Pdg;
import com.dt.dashboardservice.model.tubing.PdgRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PdgClient {

	private static final String PDG_PATH = "/v1/pdg";

	private final String serviceRootUrl;

	private final RestTemplate restTemplate;

	public PdgClient(@Value("${tubing.url}") String serviceRootUrl, RestTemplate restTemplate) {
		this.serviceRootUrl = serviceRootUrl;
		this.restTemplate = restTemplate;
	}

	public Pdg getPdg(UUID pdgId) {
		log.info("Sending a retrieve PDG request with id {}", pdgId);
		String pathVariable = "/" + pdgId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + PDG_PATH).path(pathVariable).toUriString();
		ResponseEntity<Pdg> response = restTemplate.exchange(uri, HttpMethod.GET, createHeaders(), Pdg.class);
		return response.getBody();
	}

	public List<Pdg> getAllPdgsByIdList(List<UUID> pdgIdList) {
		if (Objects.isNull(pdgIdList)) {
			return new ArrayList<>();
		}

		List<Pdg> pdgList = new ArrayList<>();
		for (UUID pdgId : pdgIdList) {
			Pdg pdg = getPdg(pdgId);
			pdgList.add(pdg);
		}
		return pdgList;
	}

	public void postPdg(PdgRequest pdgRequest, UUID tubingId) {
		log.info("Sending a create pdg request with name {} for tubing {}", pdgRequest.getName(), tubingId);
		String pathVariable = "/" + tubingId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + PDG_PATH).path(pathVariable).toUriString();
		restTemplate.postForEntity(uri, pdgRequest, Pdg.class);
	}

	public void putPdg(PdgRequest pdgRequest, UUID pdgId) {
		log.info("Sending a update pdg request with name: {}", pdgRequest.getName());
		String pathVariable = "/" + pdgId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + PDG_PATH).path(pathVariable).toUriString();
		restTemplate.put(uri, pdgRequest);
	}

	public void deletePdg(UUID pdgId) {
		log.info("Sending a delete pdg request with id: {}",pdgId);
		String pathVariable = "/" + pdgId.toString();
		String uri = UriComponentsBuilder.fromHttpUrl(serviceRootUrl + PDG_PATH).path(pathVariable).toUriString();
		restTemplate.delete(uri);
	}

	private HttpEntity<String> createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}
