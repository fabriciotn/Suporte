package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.BatchSize;

/**
 * Classe de modelo para Laudos
 * @author 12546446
 *
 */
@Entity
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Laudo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private User user;
	@OneToOne
	private Setor setor;
	@OneToOne
	private Unidade unidade;
	@Lob
	private String conclusivo;
	@Lob
	private String obs;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dt_laudo;
	@OneToOne
	@JoinColumn(unique = true)
	private Equipamento equipamento;
	
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
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public String getConclusivo() {
		return conclusivo;
	}
	public void setConclusivo(String conclusivo) {
		this.conclusivo = conclusivo;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Date getDt_laudo() {
		return dt_laudo;
	}
	public void setDt_laudo(Date dt_laudo) {
		this.dt_laudo = dt_laudo;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
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
		Laudo other = (Laudo) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
