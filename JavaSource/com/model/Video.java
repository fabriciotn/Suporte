package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.BatchSize;

/**
 * Classe de modelo para Video
 * @author 12546446
 *
 */
@Entity
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Video implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private User user;
	private String nomeSolicitante;
	private String emailSolicitante;
	@OneToOne
	private Setor setorSolicitante;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDeRegistro;
	@Temporal(TemporalType.DATE)
	private Date dataDaVideo;
	@Temporal(TemporalType.TIME)
	private Date horaDeInicioDaVideo;
	@Temporal(TemporalType.TIME)
	private Date horaDeFimDaVideo;
	private String tema;
	private String idDaSala;
	private String pinDaSala;
	private String senhaDaSala;
	@OneToOne
	private Local localDeOrigem;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "video_localDeDestino",
	joinColumns = @JoinColumn(name = "video_id"), 
	inverseJoinColumns = @JoinColumn(name = "localDeDestino_id"))
	private List<Local> locaisDeDestino;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}
	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}
	public String getEmailSolicitante() {
		return emailSolicitante;
	}
	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}
	public Setor getSetorSolicitante() {
		return setorSolicitante;
	}
	public void setSetorSolicitante(Setor setorSolicitante) {
		this.setorSolicitante = setorSolicitante;
	}
	public Date getDataDeRegistro() {
		return dataDeRegistro;
	}
	public void setDataDeRegistro(Date dataDeRegistro) {
		this.dataDeRegistro = dataDeRegistro;
	}
	public Date getDataDaVideo() {
		return dataDaVideo;
	}
	public void setDataDaVideo(Date dataDaVideo) {
		this.dataDaVideo = dataDaVideo;
	}
	public Date getHoraDeInicioDaVideo() {
		return horaDeInicioDaVideo;
	}
	public void setHoraDeInicioDaVideo(Date horaDeInicioDaVideo) {
		this.horaDeInicioDaVideo = horaDeInicioDaVideo;
	}
	public Date getHoraDeFimDaVideo() {
		return horaDeFimDaVideo;
	}
	public void setHoraDeFimDaVideo(Date horaDeFimDaVideo) {
		this.horaDeFimDaVideo = horaDeFimDaVideo;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public String getIdDaSala() {
		return idDaSala;
	}
	public void setIdDaSala(String idDaSala) {
		this.idDaSala = idDaSala;
	}
	public String getPinDaSala() {
		return pinDaSala;
	}
	public void setPinDaSala(String pinDaSala) {
		this.pinDaSala = pinDaSala;
	}
	public String getSenhaDaSala() {
		return senhaDaSala;
	}
	public void setSenhaDaSala(String senhaDaSala) {
		this.senhaDaSala = senhaDaSala;
	}
	public Local getLocalDeOrigem() {
		return localDeOrigem;
	}
	public void setLocalDeOrigem(Local localDeOrigem) {
		this.localDeOrigem = localDeOrigem;
	}
	public List<Local> getLocaisDeDestino() {
		return locaisDeDestino;
	}
	public void setLocaisDeDestino(List<Local> locaisDeDestino) {
		this.locaisDeDestino = locaisDeDestino;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Video other = (Video) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
