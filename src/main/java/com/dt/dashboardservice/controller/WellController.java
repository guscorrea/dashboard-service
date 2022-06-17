package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.dashboardservice.client.WellOrchestratorClient;
import com.dt.dashboardservice.model.WellRequest;

@Controller
public class WellController {

	@Autowired
	private WellOrchestratorClient wellOrchestratorClient;

	@GetMapping("/add-well-form")
	public ModelAndView addWellForm() {
		ModelAndView modelAndView = new ModelAndView("add-well-form");
		modelAndView.addObject("wellRequest", new WellRequest());
		return modelAndView;
	}

	@PostMapping("/save-well")
	public String saveWell(@ModelAttribute WellRequest wellRequest) {
		//TODO handle this request
		wellOrchestratorClient.postWell(wellRequest);
		return "redirect:/";
	}

	@PostMapping("/update-well")
	public String saveWell(@ModelAttribute WellRequest wellRequest, @RequestParam UUID wellId) {
		//TODO handle this request
		wellOrchestratorClient.putWell(wellRequest, wellId);
		return "redirect:/";
	}

	@GetMapping("/show-update-form")
	public ModelAndView showUpdateForm(@RequestParam UUID wellId) {
		ModelAndView modelAndView = new ModelAndView("update-well-form");
		modelAndView.addObject("wellRequest", new WellRequest());
		modelAndView.addObject("id", wellId);
		return modelAndView;
	}

}
