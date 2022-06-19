package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dt.dashboardservice.client.ComponentClient;
import com.dt.dashboardservice.client.WellOrchestratorClient;
import com.dt.dashboardservice.model.well.Well;

@Controller
public class DetailsController {

	private final WellOrchestratorClient wellOrchestratorClient;

	private final ComponentClient componentClient;

	@Autowired
	public DetailsController(WellOrchestratorClient wellOrchestratorClient, ComponentClient componentClient) {
		this.wellOrchestratorClient = wellOrchestratorClient;
		this.componentClient = componentClient;
	}

	@GetMapping("/details")
	public ModelAndView details(@RequestParam UUID wellId) {
		ModelAndView modelAndView = new ModelAndView("details");
		Well well = wellOrchestratorClient.getWell(wellId);
		modelAndView.addObject("well", well);
		return modelAndView;
	}

	@GetMapping("/remove-component")
	public String deleteWell(@RequestParam UUID componentId, @RequestParam UUID wellId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("wellId", wellId);
		componentClient.removeComponent(wellId, componentId);
		return "redirect:/details";
	}

}
