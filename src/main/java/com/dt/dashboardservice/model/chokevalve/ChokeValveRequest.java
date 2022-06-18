package com.dt.dashboardservice.model.chokevalve;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChokeValveRequest {

	@NotBlank
	String name;

	String valveInfo;

}
