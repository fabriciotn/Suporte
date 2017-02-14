package com.dao;

import com.model.Equipamento;

/**
 * Classe de acesso ao BD - Equipamento
 * @author 12546446
 *
 */
public class EquipamentoDAO extends GenericDAO<Equipamento> {

	private static final long serialVersionUID = 1L;

	public EquipamentoDAO() {
		super(Equipamento.class);
	}

	public void delete(Equipamento equipamento) {
		super.delete(equipamento.getId(), Equipamento.class);
	}
}