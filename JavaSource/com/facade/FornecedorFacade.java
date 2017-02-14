package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.FornecedorDAO;
import com.model.Fornecedor;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author 12546446
 *
 */
public class FornecedorFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private FornecedorDAO fornecedorDAO = new FornecedorDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo fornecedor
	 * @param fornecedor
	 */
	public void createFornecedor(Fornecedor fornecedor) {
		fornecedorDAO.beginTransaction();
		fornecedorDAO.save(fornecedor);
		fornecedorDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um fornecedor
	 * @param fornecedor
	 */
	public void updateFornecedor(Fornecedor fornecedor) {
		fornecedorDAO.beginTransaction();
		Fornecedor persistedFornecedor = fornecedorDAO.find(fornecedor.getId());
		if(persistedFornecedor != null){
			persistedFornecedor.setNome(fornecedor.getNome());
			persistedFornecedor.setUser(fornecedor.getUser());
		}else{
			persistedFornecedor = fornecedor;
		}
			
		fornecedorDAO.update(persistedFornecedor);
		fornecedorDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um fornecedor de acordo com o ID
	 * @param fornecedorId
	 * @return
	 */
	public Fornecedor findFornecedor(int fornecedorId) {
		fornecedorDAO.beginTransaction();
		Fornecedor fornecedor = fornecedorDAO.find(fornecedorId);
		fornecedorDAO.closeTransaction();
		return fornecedor;
	}

	/**
	 * Lista todos os fornecedores
	 * @return listaDeFornecedores
	 */
	public List<Fornecedor> listAll() {
		fornecedorDAO.beginTransaction();
		List<Fornecedor> result = fornecedorDAO.findAllAsc();
		fornecedorDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um fornecedor
	 * @param fornecedor
	 */
	public void deleteFornecedor(Fornecedor fornecedor) {
		fornecedorDAO.beginTransaction();
		Fornecedor persistedFornecedor = fornecedorDAO.findReferenceOnly(fornecedor
				.getId());
		fornecedorDAO.delete(persistedFornecedor);
		fornecedorDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		fornecedorDAO.beginTransaction();
		Query query = fornecedorDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		fornecedorDAO.closeTransaction();
		return list;
	}

}