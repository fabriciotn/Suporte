package com.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.VideoFacade;
import com.model.User;
import com.model.Video;

@RequestScoped
@ManagedBean
public class VideoMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Video video;
	private List<Video> videos;
	private VideoFacade videoFacade;
	private User usuarioLogado;

	public VideoMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public VideoFacade getVideoFacade() {
		if (videoFacade == null) {
			videoFacade = new VideoFacade();
		}

		return videoFacade;
	}

	public Video getVideo() {
		if (video == null) {
			video = new Video();
		}

		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String createVideo() {
		try {
			video.setUser(usuarioLogado);
			video.setDataDeRegistro(new Date());
			getVideoFacade().createVideo(video);
			closeDialog();
			displayInfoMessageToUser("Videoconferência gravada com sucesso!");
			loadVideos();
			resetVideo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarVideo.xhtml";
	}

	public void updateVideo() {
		try {
			if (video.getId() == 0) {
				createVideo();
			} else {
				video.setUser(usuarioLogado);
				getVideoFacade().updateVideo(video);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadVideos();
				resetVideo();

				// retorna para a listagem de Videos
				ExternalContext context = FacesContext.getCurrentInstance()
						.getExternalContext();
				context.redirect("listarVideo.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteVideo() {
		try {
			getVideoFacade().deleteVideo(video);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadVideos();
			resetVideo();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteVideo(String id) {
		int idVideo = Integer.parseInt(id);
		video = videoFacade.findVideo(idVideo);
		deleteVideo();
	}

	public List<Video> getAllVideos() {
		if (videos == null) {
			loadVideos();
		}

		return videos;
	}
	
	public List<Video> getAllVideosDesc() {
		if (videos == null) {
			videos = getVideoFacade().listAllDesc();
		}

		return videos;
	}

	private void loadVideos() {
		videos = getVideoFacade().listAll();
	}

	public void resetVideo() {
		video = new Video();
	}

	public Video pesquisaVideo() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int videoId = id;
		video = videoFacade.findVideo(videoId);

		return video;
	}
}
