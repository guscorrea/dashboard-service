package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.dashboardservice.client.ChokeValveClient;
import com.dt.dashboardservice.model.chokevalve.ChokeValveRequest;

@Controller
public class ChokeValveController {

	private final ChokeValveClient chokeValveClient;

	@Autowired
	public ChokeValveController(ChokeValveClient chokeValveClient) {
		this.chokeValveClient = chokeValveClient;
	}

	@GetMapping("/add-choke-form")
	public ModelAndView addChokeValveForm() {
		ModelAndView modelAndView = new ModelAndView("add-choke-form");
		modelAndView.addObject("chokeValveRequest", new ChokeValveRequest());
		return modelAndView;
	}

	@PostMapping("/save-choke")
	public String saveChokeValve(@ModelAttribute ChokeValveRequest chokeValveRequest) {
		//TODO handle this request
		chokeValveClient.postChokeValve(chokeValveRequest);
		return "redirect:/";
	}

	@GetMapping("/show-choke-update-form")
	public ModelAndView showUpdateForm(@RequestParam UUID chokeValveId) {
		ModelAndView modelAndView = new ModelAndView("update-choke-form");
		modelAndView.addObject("chokeValveRequest", new ChokeValveRequest());
		modelAndView.addObject("id", chokeValveId);
		return modelAndView;
	}

	@PostMapping("/update-choke-valve")
	public String updateChokeValve(@ModelAttribute ChokeValveRequest chokeValveRequest, @RequestParam UUID chokeValveId) {
		//TODO handle this request
		chokeValveClient.putChokeValve(chokeValveRequest, chokeValveId);
		return "redirect:/";
	}

	@GetMapping("/delete-choke")
	public String deleteChokeValve(@RequestParam UUID chokeValveId) {
		chokeValveClient.deleteChokeValve(chokeValveId);
		return "redirect:/";
	}

}
