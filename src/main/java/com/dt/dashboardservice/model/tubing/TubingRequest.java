package com.dt.dashboardservice.model.tubing;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TubingRequest {

	@NotBlank
	private String name;

	private String tubingInfo;

	private Boolean icvValveIsOpen;

}
