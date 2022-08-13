package com.dt.dashboardservice.controller;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dt.dashboardservice.client.AnmClient;
import com.dt.dashboardservice.client.ChokeValveClient;
import com.dt.dashboardservice.client.ComponentClient;
import com.dt.dashboardservice.client.TubingClient;
import com.dt.dashboardservice.client.WellOrchestratorClient;
import com.dt.dashboardservice.model.chokevalve.DateFilter;
import com.dt.dashboardservice.model.well.ComponentRequest;
import com.dt.dashboardservice.model.well.ComponentType;
import com.dt.dashboardservice.model.well.Well;

@Controller
public class DetailsController {

	private final WellOrchestratorClient wellOrchestratorClient;

	private final ChokeValveClient chokeValveClient;

	private final AnmClient anmClient;

	private final TubingClient tubingClient;

	private final ComponentClient componentClient;

	@Autowired
	public DetailsController(WellOrchestratorClient wellOrchestratorClient, ChokeValveClient chokeValveClient, AnmClient anmClient,
			TubingClient tubingClient, ComponentClient componentClient) {
		this.wellOrchestratorClient = wellOrchestratorClient;
		this.chokeValveClient = chokeValveClient;
		this.anmClient = anmClient;
		this.tubingClient = tubingClient;
		this.componentClient = componentClient;
	}

	@GetMapping("/details")
	public ModelAndView details(@RequestParam UUID wellId) {
		ModelAndView modelAndView = new ModelAndView("details");
		Well well = wellOrchestratorClient.getWell(wellId);
		modelAndView.addObject("well", well);
		return modelAndView;
	}

	@GetMapping("/add-component-form")
	public ModelAndView addComponentForm(@RequestParam UUID wellId) {
		ModelAndView modelAndView = new ModelAndView("add-component-form");
		modelAndView.addObject("availableComponents", Arrays.asList(ComponentType.values()));
		modelAndView.addObject("chokeValveOptions", chokeValveClient.getAllChokeValves());
		modelAndView.addObject("anmOptions", anmClient.getAllAnms());
		modelAndView.addObject("tubingOptions", tubingClient.getAllTubings());
		modelAndView.addObject("componentRequest", new ComponentRequest());
		modelAndView.addObject("id", wellId);
		return modelAndView;
	}

	@PostMapping("/add-component")
	public String updateWell(@ModelAttribute ComponentRequest componentRequest, @RequestParam UUID wellId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("wellId", wellId);
		componentClient.addComponent(wellId, componentRequest);
		return "redirect:/details";
	}

	@GetMapping("/remove-component")
	public String deleteWell(@RequestParam UUID componentId, @RequestParam UUID wellId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("wellId", wellId);
		componentClient.removeComponent(wellId, componentId);
		return "redirect:/details";
	}

	@GetMapping("/show-readings")
	public ModelAndView showReadingsForm(@RequestParam UUID componentId, @RequestParam ComponentType componentType) {
		ModelAndView modelAndView = new ModelAndView("show-readings-form");
		modelAndView.addObject("component", componentId);
		if (ComponentType.choke.equals(componentType)) {
			addChokeValveOptions(componentId, modelAndView);
		}

		if (ComponentType.anm.equals(componentType)) {
			addAnmOptions(componentId, modelAndView);
		}

		if (ComponentType.tubing.equals(componentType)) {
			addTubingOptions(componentId, modelAndView);
		}

		modelAndView.addObject("dateFilter", new DateFilter());
		return modelAndView;
	}

	@PostMapping("/date-filter")
	public String filterDate(@ModelAttribute DateFilter dateFilter, @RequestParam UUID componentId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("componentId", componentId);
		return "redirect:/show-readings";
	}

	private void addTubingOptions(UUID componentId, ModelAndView modelAndView) {
		modelAndView.addObject("pressures", tubingClient.getAllPressuresById(componentId));
		modelAndView.addObject("temperatures", tubingClient.getAllTemperaturesById(componentId));
		modelAndView.addObject("customMeasures",  tubingClient.getAllCustomMeasuresById(componentId));
		modelAndView.addObject("flows", Arrays.asList());
	}

	private void addAnmOptions(UUID componentId, ModelAndView modelAndView) {
		modelAndView.addObject("pressures", anmClient.getAllPressuresById(componentId));
		modelAndView.addObject("temperatures", anmClient.getAllTemperaturesById(componentId));
		modelAndView.addObject("customMeasures",  anmClient.getAllCustomMeasuresById(componentId));
		modelAndView.addObject("flows", Arrays.asList());
	}

	private void addChokeValveOptions(UUID componentId, ModelAndView modelAndView) {
		modelAndView.addObject("pressures", chokeValveClient.getAllPressuresById(componentId));
		modelAndView.addObject("temperatures", chokeValveClient.getAllTemperaturesById(componentId));
		modelAndView.addObject("customMeasures", chokeValveClient.getAllCustomMeasuresById(componentId));
		modelAndView.addObject("flows", chokeValveClient.getAllFlowsById(componentId));
	}

}
