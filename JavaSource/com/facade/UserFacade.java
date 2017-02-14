package com.facade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.dao.UserDAO;
import com.model.User;
import com.util.ADAuthenticator;

/**
 * Classe fachada para acesso ao banco de dados.
 * 
 * @author 12546446
 *
 */
public class UserFacade implements Serializable {

	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAO();

	/**
	 * Verifica se o usuário e senha são válidos
	 * 
	 * @param login
	 * @param password
	 * @param service
	 * @return User
	 */
	public User isValidLogin(String login, String password, boolean service) {
		userDAO.beginTransaction();
		User user = userDAO.findUserByLogin(login);
		User userAD = validaAD(login, password);

		if (user != null) {
			if (!user.getAtivo()) {
				return null;
			}
			if (userAD != null) {
				user.setName(userAD.getName());
				user.setEmail(userAD.getEmail());
				user.setUltimoAcesso(Calendar.getInstance());
				userDAO.commit();
			} else {
				return null;
			}
		} else {
			return null;
		}

		userDAO.closeTransaction();

		return user;
	}

	/**
	 * Método para realizar a validação LDAP, no Active Directory
	 * 
	 * @param login
	 * @param password
	 * @return usuario
	 */
	public User validaAD(String login, String password) {
		// instanciando a classe ADAuthenticator para fazer
		// a validação do Login e senha no servidor do AD
		ADAuthenticator autenticador = new ADAuthenticator();

		User usuarioAd = autenticador.authenticate(login, password);

		return usuarioAd;
	}

	/**
	 * Cria um novo usuário
	 * 
	 * @param user
	 */
	public void createUsuario(User user) {
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um usuário de acordo com o ID
	 * 
	 * @param userId
	 * @return usuario
	 */
	public User findUsuario(int userId) {
		userDAO.beginTransaction();
		User user = userDAO.find(userId);
		userDAO.closeTransaction();
		return user;
	}

	/**
	 * Lista todos os usuários
	 * 
	 * @return listaDeUsuarios
	 */
	public List<User> listAll() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllAsc();
		userDAO.closeTransaction();
		return result;
	}

	/**
	 * Atualiza um usuário específicos
	 * 
	 * @param user
	 */
	public void updateUsuario(User user) {
		userDAO.beginTransaction();
		User userFind = userDAO.find(user.getId());
		if (userFind != null) {
			if (user.getName() != null)
				userFind.setName(user.getName());

			if (user.getEmail() != null)
				userFind.setEmail(user.getEmail());

			if (user.getLogin() != null)
				userFind.setLogin(user.getLogin());

			if (user.getRole() != null)
				userFind.setRole(user.getRole());

			if (user.getUltimoAcesso() != null)
				userFind.setUltimoAcesso(user.getUltimoAcesso());

			userFind.setAtivo(user.getAtivo());
		} else {
			userFind = user;
		}

		userDAO.update(userFind);
		userDAO.commitAndCloseTransaction();
	}
}