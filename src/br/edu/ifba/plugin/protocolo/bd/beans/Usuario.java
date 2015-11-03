package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usuario", schema="administrativo")
@SequenceGenerator(name="sq_usuario", sequenceName="sq_usuario", schema="administrativo", allocationSize=1)
public class Usuario {
	
	private Integer id;
	private String login;
	private String senha;
	private Boolean ativo;
	private Setor setor;
	
	public Usuario() {
	}
	
	public Usuario(Integer id, String login, String senha, Boolean ativo, Setor setor) {
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.ativo = ativo;
		this.setor = setor;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_usuario")
	public Integer getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public String getSenha() {
		return senha;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsetor")
	public Setor getSetor() {
		return setor;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((setor == null) ? 0 : setor.hashCode());
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
		Usuario other = (Usuario) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (setor == null) {
			if (other.setor != null)
				return false;
		} else if (!setor.equals(other.setor))
			return false;
		return true;
	}
	
}
