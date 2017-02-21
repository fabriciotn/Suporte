package com.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.VideoDAO;
import com.model.Video;

/**
 * Classe fachada para acesso ao banco de dados.
 * 
 * @author 12546446
 *
 */
public class VideoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private VideoDAO videoDAO = new VideoDAO();
	private static SessionFactory factory;

	/**
	 * Cria uma nova video
	 * 
	 * @param video
	 */
	public void createVideo(Video video) {
		videoDAO.beginTransaction();
		videoDAO.save(video);
		videoDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza uma video
	 * 
	 * @param video
	 */
	public void updateVideo(Video video) {
		videoDAO.beginTransaction();
		Video persistedVideo = videoDAO.find(video
				.getId());
		if (persistedVideo != null) {
			persistedVideo.setUser(video.getUser());
			persistedVideo.setNomeSolicitante(video.getNomeSolicitante());
			persistedVideo.setEmailSolicitante(video.getEmailSolicitante());
			persistedVideo.setSetorSolicitante(video.getSetorSolicitante());
			persistedVideo.setDataDaVideo(video.getDataDaVideo());
			persistedVideo.setHoraDeInicioDaVideo(video.getHoraDeInicioDaVideo());
			persistedVideo.setHoraDeFimDaVideo(video.getHoraDeFimDaVideo());
			persistedVideo.setTema(video.getTema());
			persistedVideo.setIdDaSala(video.getIdDaSala());
			persistedVideo.setPinDaSala(video.getPinDaSala());
			persistedVideo.setSenhaDaSala(video.getSenhaDaSala());
			persistedVideo.setLocalDeOrigem(video.getLocalDeOrigem());
			persistedVideo.setLocaisDeDestino(video.getLocaisDeDestino());
			persistedVideo.setDataDeRegistro(new Date());
		} else {
			persistedVideo = video;
		}

		videoDAO.update(persistedVideo);
		videoDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca uma video de acordo com o ID
	 * 
	 * @param videoId
	 * @return
	 */
	public Video findVideo(int videoId) {
		videoDAO.beginTransaction();
		Video video = videoDAO.find(videoId);
		videoDAO.closeTransaction();
		return video;
	}

	/**
	 * Lista todas as videos
	 * 
	 * @return listaDeVideoes
	 */
	public List<Video> listAll() {
		videoDAO.beginTransaction();
		List<Video> result = videoDAO.findAllAsc();
		videoDAO.closeTransaction();
		return result;
	}

	/**
	 * Lista todas as videos em ordem decrescente
	 * 
	 * @return listaDeVideoes
	 */
	public List<Video> listAllDesc() {
		videoDAO.beginTransaction();
		List<Video> result = videoDAO.findAllDesc("dataDaVideo");
		videoDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta uma video
	 * 
	 * @param video
	 */
	public void deleteVideo(Video video) {
		videoDAO.beginTransaction();
		Video persistedVideo = videoDAO
				.findReferenceOnly(video.getId());
		videoDAO.delete(persistedVideo);
		videoDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * 
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		videoDAO.beginTransaction();
		Query query = videoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		videoDAO.closeTransaction();
		return list;
	}

	
	/*
	 * EM DESENVOLVIMENTO.....
	 *
	public boolean validaSeJaExisteVideoParaMesmoDiaEHoraio(){
		String sql = "select " + 
				" v.id, " + 
				" v.dataDaVideo, " + 
			    " v.horaDeInicioDaVideo, " + 
			    " v.horaDeFimDaVideo, " + 
				" v.localDeOrigem, " + 
			    " v.locaisDeDestino " + 
			" from  " + 
				" Video v ";
		List<Object[]> buscaComQuery = buscaComQuery(sql);
		for (Object[] o : buscaComQuery) {
			System.out.println("Teste: " + o);
		}
		return true;
	}
	*/
}