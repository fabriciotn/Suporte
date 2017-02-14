package com.model;

public enum Role {
	ADMIN("Administrador"),
	USUARIO("Usuário");

	private String label;
	
	private Role(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}


