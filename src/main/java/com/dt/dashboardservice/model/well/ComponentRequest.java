package com.dt.dashboardservice.model.well;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ComponentRequest {

	@NotNull
	private UUID componentId;

	@NotNull
	private ComponentType type;

}
