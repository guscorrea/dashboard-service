package com.dt.dashboardservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.dashboardservice.model.Well;

@Controller
public class WellController {

	@GetMapping("/add-well-form")
	public ModelAndView addWellForm() {
		ModelAndView modelAndView = new ModelAndView("add-well-form");
		Well newWell = new Well();
		modelAndView.addObject("well", newWell);
		return modelAndView;
	}

	@PostMapping("/save-well")
	public String saveWell(@ModelAttribute Well well) {
		//TODO Call other service to save well
		System.out.println("Call other service to save well: " + well.toString());
		return "redirect:/";
	}

	@GetMapping("/show-update-form")
	public ModelAndView showUpdateForm(@RequestParam Long wellId) {
		ModelAndView mav = new ModelAndView("add-well-form");
		//TODO Call other service to update well
		return mav;
	}

}
