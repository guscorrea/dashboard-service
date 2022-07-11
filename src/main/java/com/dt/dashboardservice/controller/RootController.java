package com.dt.dashboardservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dt.dashboardservice.client.AnmClient;
import com.dt.dashboardservice.client.ChokeValveClient;
import com.dt.dashboardservice.client.TubingClient;
import com.dt.dashboardservice.client.WellOrchestratorClient;

@Controller
@RequestMapping("/")
public class RootController {

	private final WellOrchestratorClient wellOrchestratorClient;

	private final ChokeValveClient chokeValveClient;

	private final AnmClient anmClient;

	private final TubingClient tubingClient;

	@Autowired
	public RootController(WellOrchestratorClient wellOrchestratorClient, ChokeValveClient chokeValveClient, AnmClient anmClient,
			TubingClient tubingClient) {
		this.wellOrchestratorClient = wellOrchestratorClient;
		this.chokeValveClient = chokeValveClient;
		this.anmClient = anmClient;
		this.tubingClient = tubingClient;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("wells", wellOrchestratorClient.getAllWells());
		model.addAttribute("chokeValves", chokeValveClient.getAllChokeValves());
		model.addAttribute("anms", anmClient.getAllAnms());
		model.addAttribute("tubings", tubingClient.getAllTubings());
		return "index";
	}

}
