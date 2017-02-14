package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.TipoDAO;
import com.model.Tipo;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author 12546446
 *
 */
public class TipoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoDAO tipoDAO = new TipoDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo tipo
	 * @param tipo
	 */
	public void createTipo(Tipo tipo) {
		tipoDAO.beginTransaction();
		tipoDAO.save(tipo);
		tipoDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um tipo
	 * @param tipo
	 */
	public void updateTipo(Tipo tipo) {
		tipoDAO.beginTransaction();
		Tipo persistedTipo = tipoDAO.find(tipo.getId());
		if(persistedTipo != null){
			persistedTipo.setTipo(tipo.getTipo());
			persistedTipo.setUser(tipo.getUser());
		}else{
			persistedTipo = tipo;
		}
			
		tipoDAO.update(persistedTipo);
		tipoDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um tipo de acordo com o ID
	 * @param tipoId
	 * @return
	 */
	public Tipo findTipo(int tipoId) {
		tipoDAO.beginTransaction();
		Tipo tipo = tipoDAO.find(tipoId);
		tipoDAO.closeTransaction();
		return tipo;
	}

	/**
	 * Lista todos os tipos
	 * @return listaDeTipos
	 */
	public List<Tipo> listAll() {
		tipoDAO.beginTransaction();
		List<Tipo> result = tipoDAO.findAllAsc();
		tipoDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um tipo
	 * @param tipo
	 */
	public void deleteTipo(Tipo tipo) {
		tipoDAO.beginTransaction();
		Tipo persistedTipo = tipoDAO.findReferenceOnly(tipo
				.getId());
		tipoDAO.delete(persistedTipo);
		tipoDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		tipoDAO.beginTransaction();
		Query query = tipoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		tipoDAO.closeTransaction();
		return list;
	}

}