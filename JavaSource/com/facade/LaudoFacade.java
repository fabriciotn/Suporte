package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.LaudoDAO;
import com.model.Laudo;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author 12546446
 *
 */
public class LaudoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private LaudoDAO laudoDAO = new LaudoDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo laudo
	 * @param laudo
	 */
	public void createLaudo(Laudo laudo) {
		laudoDAO.beginTransaction();
		laudoDAO.save(laudo);
		laudoDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um laudo
	 * @param laudo
	 */
	public void updateLaudo(Laudo laudo) {
		laudoDAO.beginTransaction();
		Laudo persistedLaudo = laudoDAO.find(laudo.getId());
		if(persistedLaudo != null){
			persistedLaudo.setUser(laudo.getUser());
			persistedLaudo.setSetor(laudo.getSetor());
			persistedLaudo.setUnidade(laudo.getUnidade());
			persistedLaudo.setConclusivo(laudo.getConclusivo());
			persistedLaudo.setObs(laudo.getObs());
			persistedLaudo.setEquipamento(laudo.getEquipamento());
			//persistedLaudo.setDt_laudo(laudo.getDt_laudo());
		}else{
			persistedLaudo = laudo;
		}
			
		laudoDAO.update(persistedLaudo);
		laudoDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um laudo de acordo com o ID
	 * @param laudoId
	 * @return
	 */
	public Laudo findLaudo(int laudoId) {
		laudoDAO.beginTransaction();
		Laudo laudo = laudoDAO.find(laudoId);
		laudoDAO.closeTransaction();
		return laudo;
	}

	/**
	 * Lista todos os laudos
	 * @return listaDeLaudoes
	 */
	public List<Laudo> listAll() {
		laudoDAO.beginTransaction();
		List<Laudo> result = laudoDAO.findAllAsc();
		laudoDAO.closeTransaction();
		return result;
	}
	
	/**
	 * Lista todos os laudos em ordem decrescente
	 * @return listaDeLaudoes
	 */
	public List<Laudo> listAllDesc() {
		laudoDAO.beginTransaction();
		List<Laudo> result = laudoDAO.findAllDesc("id");
		laudoDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um laudo
	 * @param laudo
	 */
	public void deleteLaudo(Laudo laudo) {
		laudoDAO.beginTransaction();
		Laudo persistedLaudo = laudoDAO.findReferenceOnly(laudo
				.getId());
		laudoDAO.delete(persistedLaudo);
		laudoDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		laudoDAO.beginTransaction();
		Query query = laudoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		laudoDAO.closeTransaction();
		return list;
	}

}