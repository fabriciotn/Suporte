package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.LocalFacade;
import com.model.Local;
import com.model.User;

@RequestScoped
@ManagedBean
public class LocalMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Local local;
	private List<Local> locais;
	private LocalFacade localFacade;
	private User usuarioLogado;

	public LocalMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public LocalFacade getLocalFacade() {
		if (localFacade == null) {
			localFacade = new LocalFacade();
		}

		return localFacade;
	}

	public Local getLocal() {
		if (local == null) {
			local = new Local();
		}

		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public String createLocal() {
		try {
			local.setUser(usuarioLogado);

			getLocalFacade().createLocal(local);
			closeDialog();
			displayInfoMessageToUser("Local " + local.getNome()
					+ " gravado com sucesso!");
			loadLocais();
			resetLocal();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarLocal.xhtml";
	}

	public void updateLocal() {
		try {
			if (local.getId() == 0) {
				createLocal();
			} else {
				local.setUser(usuarioLogado);
				getLocalFacade().updateLocal(local);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadLocais();
				resetLocal();

				// retorna para a listagem de Locais
				ExternalContext context = FacesContext.getCurrentInstance()
						.getExternalContext();
				context.redirect("listarLocais.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteLocal() {
		try {
			getLocalFacade().deleteLocal(local);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadLocais();
			resetLocal();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteLocal(String id) {
		int idLocal = Integer.parseInt(id);
		local = localFacade.findLocal(idLocal);
		deleteLocal();
	}

	public List<Local> getAllLocais() {
		if (locais == null) {
			loadLocais();
		}

		return locais;
	}

	private void loadLocais() {
		locais = getLocalFacade().listAll();
	}

	public void resetLocal() {
		local = new Local();
	}

	public Local pesquisaLocal() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int localId = id;
		local = localFacade.findLocal(localId);

		return local;
	}
}
