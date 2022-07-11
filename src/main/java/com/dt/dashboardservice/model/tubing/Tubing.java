package com.dt.dashboardservice.model.tubing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tubing {

	private UUID tubingId;

	private String name;

	private String tubingInfo;

	private List<UUID> pdgIdList;

	private boolean icvValveIsOpen;

	private LocalDateTime creationDateTime;

}
