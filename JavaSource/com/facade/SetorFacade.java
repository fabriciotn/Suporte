package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.SetorDAO;
import com.model.Setor;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author 12546446
 *
 */
public class SetorFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private SetorDAO setorDAO = new SetorDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo setor
	 * @param setor
	 */
	public void createSetor(Setor setor) {
		setorDAO.beginTransaction();
		setorDAO.save(setor);
		setorDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um setor
	 * @param setor
	 */
	public void updateSetor(Setor setor) {
		setorDAO.beginTransaction();
		Setor persistedSetor = setorDAO.find(setor.getId());
		if(persistedSetor != null){
			persistedSetor.setNome(setor.getNome());
			persistedSetor.setUser(setor.getUser());
		}else{
			persistedSetor = setor;
		}
			
		setorDAO.update(persistedSetor);
		setorDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um setor de acordo com o ID
	 * @param setorId
	 * @return
	 */
	public Setor findSetor(int setorId) {
		setorDAO.beginTransaction();
		Setor setor = setorDAO.find(setorId);
		setorDAO.closeTransaction();
		return setor;
	}

	/**
	 * Lista todos os setores
	 * @return listaDeSetores
	 */
	public List<Setor> listAll() {
		setorDAO.beginTransaction();
		List<Setor> result = setorDAO.findAllAsc();
		setorDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um setor
	 * @param setor
	 */
	public void deleteSetor(Setor setor) {
		setorDAO.beginTransaction();
		Setor persistedSetor = setorDAO.findReferenceOnly(setor
				.getId());
		setorDAO.delete(persistedSetor);
		setorDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		setorDAO.beginTransaction();
		Query query = setorDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		setorDAO.closeTransaction();
		return list;
	}

}