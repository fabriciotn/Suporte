package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.EquipamentoDAO;
import com.model.Equipamento;

/**
 * Classe fachada para acesso ao banco de dados.
 * 
 * @author 12546446
 *
 */
public class EquipamentoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
	private static SessionFactory factory;

	/**
	 * Cria um novo equipamento
	 * 
	 * @param equipamento
	 */
	public void createEquipamento(Equipamento equipamento) {
		equipamentoDAO.beginTransaction();
		equipamentoDAO.save(equipamento);
		equipamentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um equipamento
	 * 
	 * @param equipamento
	 */
	public void updateEquipamento(Equipamento equipamento) {
		equipamentoDAO.beginTransaction();
		Equipamento persistedEquipamento = equipamentoDAO.find(equipamento
				.getId());
		if (persistedEquipamento != null) {
			persistedEquipamento.setUser(equipamento.getUser());
			persistedEquipamento.setFornecedor(equipamento.getFornecedor());
			persistedEquipamento.setTipo(equipamento.getTipo());
			persistedEquipamento.setPatrimonio(equipamento.getPatrimonio());
			persistedEquipamento.setSerie(equipamento.getSerie());
			persistedEquipamento.setMarca(equipamento.getMarca());
			persistedEquipamento.setModelo(equipamento.getModelo());
			persistedEquipamento.setDataDeCompra(equipamento.getDataDeCompra());
			persistedEquipamento.setDataFinalDaGarantia(equipamento
					.getDataFinalDaGarantia());
			// persistedEquipamento.setDt_equipamento(equipamento.getDt_equipamento());
		} else {
			persistedEquipamento = equipamento;
		}

		equipamentoDAO.update(persistedEquipamento);
		equipamentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um equipamento de acordo com o ID
	 * 
	 * @param equipamentoId
	 * @return
	 */
	public Equipamento findEquipamento(int equipamentoId) {
		equipamentoDAO.beginTransaction();
		Equipamento equipamento = equipamentoDAO.find(equipamentoId);
		equipamentoDAO.closeTransaction();
		return equipamento;
	}

	/**
	 * Lista todos os equipamentos
	 * 
	 * @return listaDeEquipamentoes
	 */
	public List<Equipamento> listAll() {
		equipamentoDAO.beginTransaction();
		List<Equipamento> result = equipamentoDAO.findAllAsc();
		equipamentoDAO.closeTransaction();
		return result;
	}

	/**
	 * Lista todos os equipamentos em ordem decrescente
	 * 
	 * @return listaDeEquipamentoes
	 */
	public List<Equipamento> listAllDesc() {
		equipamentoDAO.beginTransaction();
		List<Equipamento> result = equipamentoDAO.findAllDesc("id");
		equipamentoDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um equipamento
	 * 
	 * @param equipamento
	 */
	public void deleteEquipamento(Equipamento equipamento) {
		equipamentoDAO.beginTransaction();
		Equipamento persistedEquipamento = equipamentoDAO
				.findReferenceOnly(equipamento.getId());
		equipamentoDAO.delete(persistedEquipamento);
		equipamentoDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * 
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		equipamentoDAO.beginTransaction();
		Query query = equipamentoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		equipamentoDAO.closeTransaction();
		return list;
	}

}