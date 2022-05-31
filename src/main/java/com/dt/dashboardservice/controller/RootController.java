package com.dt.dashboardservice.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dt.dashboardservice.client.WellOrchestratorClient;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private WellOrchestratorClient wellOrchestratorClient;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("pageTitle", "Digital Twin - Virtual Well Dashboard");
		model.addAttribute("wells", Arrays.asList("Test Well number 1", "RJ-380", "RJ-390"));
		return "index";
	}

}
