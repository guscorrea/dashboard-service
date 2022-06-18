package com.dt.dashboardservice.model.well;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WellRequest {

	@NotBlank
	String name;

	String wellInfo;

}
