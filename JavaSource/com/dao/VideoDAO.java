package com.dao;

import com.model.Video;

/**
 * Classe de acesso ao BD - Video
 * @author 12546446
 *
 */
public class VideoDAO extends GenericDAO<Video> {

	private static final long serialVersionUID = 1L;

	public VideoDAO() {
		super(Video.class);
	}

	public void delete(Video video) {
		super.delete(video.getId(), Video.class);
	}
}