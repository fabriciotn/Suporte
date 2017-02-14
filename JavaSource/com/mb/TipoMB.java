package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.TipoFacade;
import com.model.Tipo;
import com.model.User;

@RequestScoped
@ManagedBean
public class TipoMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tipo tipo;
	private List<Tipo> tipos;
	private TipoFacade tipoFacade;
	private User usuarioLogado;
	
	public TipoMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public TipoFacade getTipoFacade() {
		if (tipoFacade == null) {
			tipoFacade = new TipoFacade();
		}

		return tipoFacade;
	}

	public Tipo getTipo() {
		if (tipo == null) {
			tipo = new Tipo();
		}

		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String createTipo() {
		try {
			tipo.setUser(usuarioLogado);

			getTipoFacade().createTipo(tipo);
			closeDialog();
			displayInfoMessageToUser("Tipo " + tipo.getTipo() + " gravado com sucesso!");
			loadTipos();
			resetTipo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarTipo.xhtml";
	}

	public void updateTipo() {
		try {		
			if (tipo.getId() == 0) {
				createTipo();
			} else {
				tipo.setUser(usuarioLogado);
				getTipoFacade().updateTipo(tipo);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadTipos();
				resetTipo();

				// retorna para a listagem de Tipos
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarTipos.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteTipo() {
		try {
			getTipoFacade().deleteTipo(tipo);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadTipos();
			resetTipo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteTipo(String id) {
		int idTipo = Integer.parseInt(id);
		tipo = tipoFacade.findTipo(idTipo);
		deleteTipo();
	}

	public List<Tipo> getAllTipos() {
		if (tipos == null) {
			loadTipos();
		}

		return tipos;
	}

	private void loadTipos() {
		tipos = getTipoFacade().listAll();
	}

	public void resetTipo() {
		tipo = new Tipo();
	}

	public Tipo pesquisaTipo() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int tipoId = id;
		tipo = tipoFacade.findTipo(tipoId);

		return tipo;
	}
}
