package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.FornecedorFacade;
import com.model.Fornecedor;
import com.model.User;

@RequestScoped
@ManagedBean
public class FornecedorMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private FornecedorFacade fornecedorFacade;
	private User usuarioLogado;
	
	public FornecedorMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public FornecedorFacade getFornecedorFacade() {
		if (fornecedorFacade == null) {
			fornecedorFacade = new FornecedorFacade();
		}

		return fornecedorFacade;
	}

	public Fornecedor getFornecedor() {
		if (fornecedor == null) {
			fornecedor = new Fornecedor();
		}

		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String createFornecedor() {
		try {
			fornecedor.setUser(usuarioLogado);

			getFornecedorFacade().createFornecedor(fornecedor);
			closeDialog();
			displayInfoMessageToUser("Fornecedor " + fornecedor.getNome() + " gravado com sucesso!");
			loadFornecedores();
			resetFornecedor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarFornecedor.xhtml";
	}

	public void updateFornecedor() {
		try {		
			if (fornecedor.getId() == 0) {
				createFornecedor();
			} else {
				fornecedor.setUser(usuarioLogado);
				getFornecedorFacade().updateFornecedor(fornecedor);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadFornecedores();
				resetFornecedor();

				// retorna para a listagem de Fornecedores
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarFornecedores.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteFornecedor() {
		try {
			getFornecedorFacade().deleteFornecedor(fornecedor);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadFornecedores();
			resetFornecedor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteFornecedor(String id) {
		int idFornecedor = Integer.parseInt(id);
		fornecedor = fornecedorFacade.findFornecedor(idFornecedor);
		deleteFornecedor();
	}

	public List<Fornecedor> getAllFornecedores() {
		if (fornecedores == null) {
			loadFornecedores();
		}

		return fornecedores;
	}

	private void loadFornecedores() {
		fornecedores = getFornecedorFacade().listAll();
	}

	public void resetFornecedor() {
		fornecedor = new Fornecedor();
	}

	public Fornecedor pesquisaFornecedor() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int fornecedorId = id;
		fornecedor = fornecedorFacade.findFornecedor(fornecedorId);

		return fornecedor;
	}
}
