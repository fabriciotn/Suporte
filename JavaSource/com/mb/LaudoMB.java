package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Reports.ImpressaoLaudo;
import com.facade.LaudoFacade;
import com.model.Laudo;
import com.model.User;

@RequestScoped
@ManagedBean
public class LaudoMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Laudo laudo;
	private List<Laudo> laudos;
	private LaudoFacade laudoFacade;
	private User usuarioLogado;

	public LaudoMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public LaudoFacade getLaudoFacade() {
		if (laudoFacade == null) {
			laudoFacade = new LaudoFacade();
		}

		return laudoFacade;
	}

	public Laudo getLaudo() {
		if (laudo == null) {
			laudo = new Laudo();
		}

		return laudo;
	}

	public void setLaudo(Laudo laudo) {
		this.laudo = laudo;
	}

	public String createLaudo() {
		try {
			laudo.setUser(usuarioLogado);

			laudo.setDt_laudo(new Date());

			getLaudoFacade().createLaudo(laudo);
			closeDialog();
			displayInfoMessageToUser("Laudo " + laudo.getUnidade().getSigla()
					+ laudo.getId() + " gravado com sucesso!");
			loadLaudos();
			resetLaudo();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("com.model.laudo")) {
				keepDialogOpen();
				displayErrorMessageToUser("Atenção: Já existe um laudo para o patrimônio "
						+ laudo.getEquipamento().getPatrimonio() + "!");
			} else {
				keepDialogOpen();
				displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
				e.printStackTrace();
			}
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarLaudo.xhtml";
	}

	public void updateLaudo() {
		try {
			if (laudo.getId() == 0) {
				createLaudo();
			} else {
				laudo.setUser(usuarioLogado);

				getLaudoFacade().updateLaudo(laudo);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadLaudos();
				resetLaudo();

				// retorna para a listagem de Laudos
				ExternalContext context = FacesContext.getCurrentInstance()
						.getExternalContext();
				context.redirect("listarLaudos.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteLaudo() {
		try {
			getLaudoFacade().deleteLaudo(laudo);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadLaudos();
			resetLaudo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteLaudo(String id) {
		int idLaudo = Integer.parseInt(id);
		laudo = laudoFacade.findLaudo(idLaudo);
		deleteLaudo();
	}

	public List<Laudo> getAllLaudos() {
		if (laudos == null) {
			loadLaudos();
		}

		return laudos;
	}

	public List<Laudo> getAllLaudosDesc() {
		if (laudos == null) {
			loadLaudosDesc();
		}

		return laudos;
	}

	private void loadLaudos() {
		laudos = getLaudoFacade().listAll();
	}

	private void loadLaudosDesc() {
		laudos = getLaudoFacade().listAllDesc();
	}

	public void resetLaudo() {
		laudo = new Laudo();
	}

	public Laudo pesquisaLaudo() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int laudoId = id;
		laudo = laudoFacade.findLaudo(laudoId);

		return laudo;
	}

	public void geraLaudo() {
		ImpressaoLaudo relat = new ImpressaoLaudo();
		byte[] b = relat.imprimeRelatorio(laudo.getUser(), laudo.getId());

		HttpServletResponse res = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		res.setContentType("application/pdf");
		// Código abaixo gerar o relatório e disponibiliza diretamente na página
		res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");
		// Código abaixo gerar o relatório e disponibiliza para o cliente baixar
		// ou salvar
		// res.setHeader("Content-disposition",
		// "attachment;filename=arquivo.pdf");
		try {
			res.getOutputStream().write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.getCharacterEncoding();
		FacesContext.getCurrentInstance().responseComplete();
	}
}
