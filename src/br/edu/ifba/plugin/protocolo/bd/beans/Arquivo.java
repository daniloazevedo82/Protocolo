package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "arquivo", schema = "administrativo")
@SequenceGenerator(name="sq_arquivo", sequenceName="sq_arquivo", schema="administrativo", allocationSize=1)
public class Arquivo {

	private Integer id;
	private String nome;
	private String caminho;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_arquivo")
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getCaminho() {
		return caminho;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caminho == null) ? 0 : caminho.hashCode());
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
		Arquivo other = (Arquivo) obj;
		if (caminho == null) {
			if (other.caminho != null)
				return false;
		} else if (!caminho.equals(other.caminho))
			return false;
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
