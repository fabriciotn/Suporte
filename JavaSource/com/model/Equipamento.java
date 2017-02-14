package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.BatchSize;

/**
 * Classe de modelo para Equipamento
 * @author 12546446
 *
 */
@Entity
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Equipamento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private Fornecedor fornecedor;
	@OneToOne
	private Tipo tipo;
	@Column(unique = true)
	private Long patrimonio;
	private String serie;
	private String marca;
	private String modelo;
	@OneToOne
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDeCompra;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFinalDaGarantia;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Long getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(Long patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Date getDataDeCompra() {
		return dataDeCompra;
	}
	public void setDataDeCompra(Date dataDeCompra) {
		this.dataDeCompra = dataDeCompra;
	}
	public Date getDataFinalDaGarantia() {
		return dataFinalDaGarantia;
	}
	public void setDataFinalDaGarantia(Date dataFinalDaGarantia) {
		this.dataFinalDaGarantia = dataFinalDaGarantia;
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
		Equipamento other = (Equipamento) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
