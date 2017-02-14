package com.dao;

import com.model.Laudo;


/**
 * Classe de acesso ao BD - Laudo
 * @author 12546446
 *
 */
public class LaudoDAO extends GenericDAO<Laudo> {

	private static final long serialVersionUID = 1L;

	public LaudoDAO() {
		super(Laudo.class);
	}

	public void delete(Laudo laudo) {
		super.delete(laudo.getId(), Laudo.class);
	}
}