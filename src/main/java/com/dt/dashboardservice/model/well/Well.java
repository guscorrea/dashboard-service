package com.dt.dashboardservice.model.well;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Well {

	private UUID wellId;

	private String name;

	private String wellInfo;

	private Map<UUID, ComponentType> components;

	private LocalDateTime creationDateTime;

}
