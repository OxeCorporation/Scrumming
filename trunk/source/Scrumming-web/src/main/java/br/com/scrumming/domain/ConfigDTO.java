package br.com.scrumming.domain;

import java.io.Serializable;

import br.com.scrumming.domain.enuns.ConfigEnum;

public class ConfigDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Team team;
	private ConfigEnum configEnum;
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public ConfigEnum getConfigEnum() {
		return configEnum;
	}
	public void setConfigEnum(ConfigEnum configEnum) {
		this.configEnum = configEnum;
	}
}