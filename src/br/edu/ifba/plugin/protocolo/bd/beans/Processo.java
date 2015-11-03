package br.edu.ifba.plugin.protocolo.bd.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="processo", schema="administrativo")
@Inheritance(strategy=InheritanceType.JOINED)
@SequenceGenerator(name="sq_processo", sequenceName="sq_processo", schema="administrativo", allocationSize=1)
public class Processo {

	protected Integer id;
	protected Date data;
	protected TipoProcesso tipoProcesso;
	
	public Processo() {
	}
	
	public Processo(Integer id, Date data, TipoProcesso tipoProcesso) {
		this.id = id;
		this.data = data;
		this.tipoProcesso = tipoProcesso;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_processo")
	public Integer getId() {
		return id;
	}
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipoprocesso")
	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	@Override
	public String toString() {
		return "Processo [id=" + id + ", data=" + data + ", tipoProcesso=" + tipoProcesso + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoProcesso == null) ? 0 : tipoProcesso.hashCode());
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
		Processo other = (Processo) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoProcesso == null) {
			if (other.tipoProcesso != null)
				return false;
		} else if (!tipoProcesso.equals(other.tipoProcesso))
			return false;
		return true;
	}
	
}
