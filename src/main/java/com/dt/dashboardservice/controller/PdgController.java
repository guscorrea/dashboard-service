package com.dt.dashboardservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dt.dashboardservice.client.PdgClient;
import com.dt.dashboardservice.model.tubing.PdgRequest;

@Controller
public class PdgController {

	private final PdgClient pdgClient;

	@Autowired
	public PdgController(PdgClient pdgClient) {
		this.pdgClient = pdgClient;
	}

	@GetMapping("/add-pdg-form")
	public ModelAndView addPdgForm(@RequestParam UUID tubingId) {
		ModelAndView modelAndView = new ModelAndView("add-pdg-form");
		modelAndView.addObject("pdgRequest", new PdgRequest());
		modelAndView.addObject("id", tubingId);
		return modelAndView;
	}

	@PostMapping("/save-pdg")
	public String savePdg(@ModelAttribute PdgRequest pdgRequest, @RequestParam UUID tubingId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("tubingId", tubingId);
		pdgClient.postPdg(pdgRequest, tubingId);
		return "redirect:/tubing-details";
	}

	@GetMapping("/show-pdg-update-form")
	public ModelAndView showPdgUpdateForm(@RequestParam UUID pdgId, @RequestParam UUID tubingId) {
		ModelAndView modelAndView = new ModelAndView("update-pdg-form");
		modelAndView.addObject("pdgRequest", new PdgRequest());
		modelAndView.addObject("pdgId", pdgId);
		modelAndView.addObject("tubingId", tubingId);
		return modelAndView;
	}

	@PostMapping("/update-pdg")
	public String updatePdg(@ModelAttribute PdgRequest pdgRequest, @RequestParam UUID pdgId, @RequestParam UUID tubingId,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("tubingId", tubingId);
		pdgClient.putPdg(pdgRequest, pdgId);
		return "redirect:/tubing-details";
	}

	@GetMapping("/delete-pdg")
	public String deletePdg(@RequestParam UUID pdgId, @RequestParam UUID tubingId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("tubingId", tubingId);
		pdgClient.deletePdg(pdgId);
		return "redirect:/tubing-details";
	}

}
