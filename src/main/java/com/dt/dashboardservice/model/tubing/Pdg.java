package com.dt.dashboardservice.model.tubing;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pdg {

	private UUID pdgId;

	private String name;

	private String pdgInfo;

	private LocalDateTime creationDateTime;

}
