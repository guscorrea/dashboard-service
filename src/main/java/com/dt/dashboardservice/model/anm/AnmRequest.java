package com.dt.dashboardservice.model.anm;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AnmRequest {

	@NotBlank
	private String name;

	private String anmInfo;

	private Boolean pxoValveIsOpen;

	private Boolean xoValveIsOpen;

	private Boolean w1ValveIsOpen;

	private Boolean w2ValveIsOpen;

	private Boolean m1ValveIsOpen;

	private Boolean m2ValveIsOpen;

}
