package com.dt.dashboardservice.model.chokevalve;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChokeValve {

	private UUID chokeValveId;

	private String name;

	private String valveInfo;

	private LocalDateTime creationDateTime;

}
