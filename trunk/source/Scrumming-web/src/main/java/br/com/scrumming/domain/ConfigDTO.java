package br.com.scrumming.domain;

import java.io.Serializable;

import br.com.scrumming.domain.enuns.ConfigEnum;

public class ConfigDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	public Team team;
	public ConfigEnum configEnum;
}