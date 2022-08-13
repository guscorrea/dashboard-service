package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.dashboardservice.client.AnmClient;
import com.dt.dashboardservice.model.anm.AnmRequest;

@Controller
public class AnmController {

	private final AnmClient anmClient;

	@Autowired
	public AnmController(AnmClient anmClient) {
		this.anmClient = anmClient;
	}

	@GetMapping("/add-anm-form")
	public ModelAndView addAnmForm() {
		ModelAndView modelAndView = new ModelAndView("add-anm-form");
		modelAndView.addObject("anmRequest", new AnmRequest());
		return modelAndView;
	}

	@PostMapping("/save-anm")
	public String saveAnm(@ModelAttribute AnmRequest anmRequest) {
		anmClient.postAnm(anmRequest);
		return "redirect:/";
	}

	@GetMapping("/show-anm-update-form")
	public ModelAndView showUpdateForm(@RequestParam UUID anmId) {
		ModelAndView modelAndView = new ModelAndView("update-anm-form");
		modelAndView.addObject("anmRequest", new AnmRequest());
		modelAndView.addObject("id", anmId);
		return modelAndView;
	}

	@PostMapping("/update-anm")
	public String updateAnm(@ModelAttribute AnmRequest anmRequest, @RequestParam UUID anmId) {
		anmClient.putAnm(anmRequest, anmId);
		return "redirect:/";
	}

	@GetMapping("/delete-anm")
	public String deleteAnm(@RequestParam UUID anmId) {
		anmClient.deleteAnm(anmId);
		return "redirect:/";
	}

}
