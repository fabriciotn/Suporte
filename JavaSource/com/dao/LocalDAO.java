package com.dao;

import com.model.Local;

/**
 * Classe de acesso ao BD - Local
 * @author 12546446
 *
 */
public class LocalDAO extends GenericDAO<Local> {

	private static final long serialVersionUID = 1L;

	public LocalDAO() {
		super(Local.class);
	}

	public void delete(Local local) {
		super.delete(local.getId(), Local.class);
	}
}