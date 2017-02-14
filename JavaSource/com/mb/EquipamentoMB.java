package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.EquipamentoFacade;
import com.model.Equipamento;
import com.model.User;

@RequestScoped
@ManagedBean
public class EquipamentoMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Equipamento equipamento;
	private List<Equipamento> equipamentos;
	private EquipamentoFacade equipamentoFacade;
	private User usuarioLogado;

	public EquipamentoMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public EquipamentoFacade getEquipamentoFacade() {
		if (equipamentoFacade == null) {
			equipamentoFacade = new EquipamentoFacade();
		}

		return equipamentoFacade;
	}

	public Equipamento getEquipamento() {
		if (equipamento == null) {
			equipamento = new Equipamento();
		}

		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public String createEquipamento() {
		try {
			equipamento.setUser(usuarioLogado);

			getEquipamentoFacade().createEquipamento(equipamento);
			closeDialog();
			displayInfoMessageToUser("Equipamento patrimônio "
					+ equipamento.getPatrimonio() + " gravado com sucesso!");
			loadEquipamentos();
			resetEquipamento();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("com.model.equipamento")) {
				keepDialogOpen();
				displayErrorMessageToUser("Atenção: o patrimônio "
						+ equipamento.getPatrimonio() + " já está cadastrado!");
			} else {
				keepDialogOpen();
				displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
				e.printStackTrace();
			}
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarEquipamento.xhtml";
	}

	public void updateEquipamento() {
		try {
			if (equipamento.getId() == 0) {
				createEquipamento();
			} else {
				equipamento.setUser(usuarioLogado);
				getEquipamentoFacade().updateEquipamento(equipamento);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadEquipamentos();
				resetEquipamento();

				// retorna para a listagem de Equipamentos
				ExternalContext context = FacesContext.getCurrentInstance()
						.getExternalContext();
				context.redirect("listarEquipamentos.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteEquipamento() {
		try {
			getEquipamentoFacade().deleteEquipamento(equipamento);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadEquipamentos();
			resetEquipamento();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteEquipamento(String id) {
		int idEquipamento = Integer.parseInt(id);
		equipamento = equipamentoFacade.findEquipamento(idEquipamento);
		deleteEquipamento();
	}

	public List<Equipamento> getAllEquipamentos() {
		if (equipamentos == null) {
			loadEquipamentos();
		}

		return equipamentos;
	}

	private void loadEquipamentos() {
		equipamentos = getEquipamentoFacade().listAll();
	}

	public void resetEquipamento() {
		equipamento = new Equipamento();
	}

	public Equipamento pesquisaEquipamento() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int equipamentoId = id;
		equipamento = equipamentoFacade.findEquipamento(equipamentoId);

		return equipamento;
	}
}
