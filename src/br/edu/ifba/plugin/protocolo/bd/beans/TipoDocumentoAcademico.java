package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tipodocumentoacademico", schema="academico")
@SequenceGenerator(name="sq_tipodocumentoacademico", sequenceName="sq_tipodocumentoacademico", schema="academico", allocationSize=1)
public class TipoDocumentoAcademico {

	private Integer id;
	private String nome;
	
	public TipoDocumentoAcademico(){
	}
	
	public TipoDocumentoAcademico(Integer id){
		this.id = id;
	}
	
	public TipoDocumentoAcademico(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_tipodocumentoacademico")
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		TipoDocumentoAcademico other = (TipoDocumentoAcademico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
