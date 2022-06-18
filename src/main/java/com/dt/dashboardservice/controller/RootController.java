package com.dt.dashboardservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dt.dashboardservice.client.ChokeValveClient;
import com.dt.dashboardservice.client.WellOrchestratorClient;

@Controller
@RequestMapping("/")
public class RootController {

	private final WellOrchestratorClient wellOrchestratorClient;

	private final ChokeValveClient chokeValveClient;

	@Autowired
	public RootController(WellOrchestratorClient wellOrchestratorClient, ChokeValveClient chokeValveClient) {
		this.wellOrchestratorClient = wellOrchestratorClient;
		this.chokeValveClient = chokeValveClient;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("wells", wellOrchestratorClient.getAllWells());
		model.addAttribute("chokeValves", chokeValveClient.getAllChokeValves());
		return "index";
	}

}
