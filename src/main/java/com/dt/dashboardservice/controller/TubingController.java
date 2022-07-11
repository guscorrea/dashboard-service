package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.dashboardservice.client.TubingClient;
import com.dt.dashboardservice.model.tubing.TubingRequest;

@Controller
public class TubingController {

	private final TubingClient tubingClient;

	@Autowired
	public TubingController(TubingClient tubingClient) {
		this.tubingClient = tubingClient;
	}

	@GetMapping("/add-tubing-form")
	public ModelAndView addTubingForm() {
		ModelAndView modelAndView = new ModelAndView("add-tubing-form");
		modelAndView.addObject("tubingRequest", new TubingRequest());
		return modelAndView;
	}

	@PostMapping("/save-tubing")
	public String saveTubing(@ModelAttribute TubingRequest tubingRequest) {
		//TODO handle this request
		tubingClient.postTubing(tubingRequest);
		return "redirect:/";
	}

	@GetMapping("/show-tubing-update-form")
	public ModelAndView showUpdateForm(@RequestParam UUID tubingId) {
		ModelAndView modelAndView = new ModelAndView("update-tubing-form");
		modelAndView.addObject("tubingRequest", new TubingRequest());
		modelAndView.addObject("id", tubingId);
		return modelAndView;
	}

	@PostMapping("/update-tubing")
	public String updateTubing(@ModelAttribute TubingRequest tubingRequest, @RequestParam UUID tubingId) {
		//TODO handle this request
		tubingClient.putTubing(tubingRequest, tubingId);
		return "redirect:/";
	}

	@GetMapping("/delete-tubing")
	public String deleteTubing(@RequestParam UUID tubingId) {
		tubingClient.deleteTubing(tubingId);
		return "redirect:/";
	}

}
