package com.dao;

import com.model.Fornecedor;

/**
 * Classe de acesso ao BD - Fornecedor
 * @author 12546446
 *
 */
public class FornecedorDAO extends GenericDAO<Fornecedor> {

	private static final long serialVersionUID = 1L;

	public FornecedorDAO() {
		super(Fornecedor.class);
	}

	public void delete(Fornecedor fornecedor) {
		super.delete(fornecedor.getId(), Fornecedor.class);
	}
}