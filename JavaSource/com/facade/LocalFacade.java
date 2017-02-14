package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.LocalDAO;
import com.model.Local;

/**
 * Classe fachada para acesso ao banco de dados.
 * 
 * @author 12546446
 *
 */
public class LocalFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDAO localDAO = new LocalDAO();
	private static SessionFactory factory;

	/**
	 * Cria um novo local
	 * 
	 * @param local
	 */
	public void createLocal(Local local) {
		localDAO.beginTransaction();
		localDAO.save(local);
		localDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um local
	 * 
	 * @param local
	 */
	public void updateLocal(Local local) {
		localDAO.beginTransaction();
		Local persistedLocal = localDAO.find(local
				.getId());
		if (persistedLocal != null) {
			persistedLocal.setUser(local.getUser());
			persistedLocal.setUnidade(local.getUnidade());
			persistedLocal.setNome(local.getNome());
			persistedLocal.setIp(local.getIp());
		} else {
			persistedLocal = local;
		}

		localDAO.update(persistedLocal);
		localDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um local de acordo com o ID
	 * 
	 * @param localId
	 * @return
	 */
	public Local findLocal(int localId) {
		localDAO.beginTransaction();
		Local local = localDAO.find(localId);
		localDAO.closeTransaction();
		return local;
	}

	/**
	 * Lista todos os locais
	 * 
	 * @return listaDeLocais
	 */
	public List<Local> listAll() {
		localDAO.beginTransaction();
		List<Local> result = localDAO.findAllAsc();
		localDAO.closeTransaction();
		return result;
	}

	/**
	 * Lista todos os locais em ordem decrescente
	 * 
	 * @return listaDeLocais
	 */
	public List<Local> listAllDesc() {
		localDAO.beginTransaction();
		List<Local> result = localDAO.findAllDesc();
		localDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um local
	 * 
	 * @param local
	 */
	public void deleteLocal(Local local) {
		localDAO.beginTransaction();
		Local persistedLocal = localDAO
				.findReferenceOnly(local.getId());
		localDAO.delete(persistedLocal);
		localDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * 
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		localDAO.beginTransaction();
		Query query = localDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		localDAO.closeTransaction();
		return list;
	}

}