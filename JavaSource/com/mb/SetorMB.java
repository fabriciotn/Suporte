package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.SetorFacade;
import com.model.Setor;
import com.model.User;

@RequestScoped
@ManagedBean
public class SetorMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Setor setor;
	private List<Setor> setores;
	private SetorFacade setorFacade;
	private User usuarioLogado;
	
	public SetorMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public SetorFacade getSetorFacade() {
		if (setorFacade == null) {
			setorFacade = new SetorFacade();
		}

		return setorFacade;
	}

	public Setor getSetor() {
		if (setor == null) {
			setor = new Setor();
		}

		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String createSetor() {
		try {
			setor.setUser(usuarioLogado);

			getSetorFacade().createSetor(setor);
			closeDialog();
			displayInfoMessageToUser("Setor " + setor.getNome() + " gravado com sucesso!");
			loadSetores();
			resetSetor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarSetor.xhtml";
	}

	public void updateSetor() {
		try {		
			if (setor.getId() == 0) {
				createSetor();
			} else {
				setor.setUser(usuarioLogado);
				getSetorFacade().updateSetor(setor);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadSetores();
				resetSetor();

				// retorna para a listagem de Setores
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarSetores.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteSetor() {
		try {
			getSetorFacade().deleteSetor(setor);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadSetores();
			resetSetor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteSetor(String id) {
		int idSetor = Integer.parseInt(id);
		setor = setorFacade.findSetor(idSetor);
		deleteSetor();
	}

	public List<Setor> getAllSetores() {
		if (setores == null) {
			loadSetores();
		}

		return setores;
	}

	private void loadSetores() {
		setores = getSetorFacade().listAll();
	}

	public void resetSetor() {
		setor = new Setor();
	}

	public Setor pesquisaSetor() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int setorId = id;
		setor = setorFacade.findSetor(setorId);

		return setor;
	}
}
