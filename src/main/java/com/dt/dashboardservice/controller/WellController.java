package com.dt.dashboardservice.controller;

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
		WellRequest newWell = new WellRequest();
		modelAndView.addObject("wellRequest", newWell);
		return modelAndView;
	}

	@PostMapping("/save-well")
	public String saveWell(@ModelAttribute WellRequest wellRequest) {
		//TODO handle this request
		wellOrchestratorClient.postWell(wellRequest);
		System.out.println("Call other service to save well: " + wellRequest.toString());
		return "redirect:/";
	}

	@GetMapping("/show-update-form")
	public ModelAndView showUpdateForm(@RequestParam Long wellId) {
		ModelAndView mav = new ModelAndView("add-well-form");
		//TODO Call other service to update well
		return mav;
	}

}
