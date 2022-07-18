package com.dt.dashboardservice.model.tubing;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PdgRequest {

	@NotBlank
	private String name;

	private String pdgInfo;

}
