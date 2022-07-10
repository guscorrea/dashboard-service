package com.dt.dashboardservice.model.anm;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anm {

	private UUID anmId;

	private String name;

	private String anmInfo;

	private boolean pxoValveIsOpen;

	private boolean xoValveIsOpen;

	private boolean w1ValveIsOpen;

	private boolean w2ValveIsOpen;

	private boolean m1ValveIsOpen;

	private boolean m2ValveIsOpen;

	private LocalDateTime creationDateTime;

}
