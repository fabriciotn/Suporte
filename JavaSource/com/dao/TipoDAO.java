package com.dao;

import com.model.Tipo;

/**
 * Classe de acesso ao BD - Tipo
 * @author 12546446
 *
 */
public class TipoDAO extends GenericDAO<Tipo> {

	private static final long serialVersionUID = 1L;

	public TipoDAO() {
		super(Tipo.class);
	}

	public void delete(Tipo tipo) {
		super.delete(tipo.getId(), Tipo.class);
	}
}