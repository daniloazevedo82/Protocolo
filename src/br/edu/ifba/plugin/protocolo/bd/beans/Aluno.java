package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="aluno", schema="academico")
public class Aluno extends Pessoa {
	
	private String matricula;
	private Curso curso;
	
	public Aluno() {
	}
	
	public Aluno(Integer id) {
		this.id = id;
	}
	
	public Aluno(Integer id, String nome, String matricula, Curso curso) {
		this.id = id;
		this.nome = nome;
		this.matricula = matricula;
		this.curso = curso;
	}
	
	public String getMatricula() {
		return matricula;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcurso")
	public Curso getCurso() {
		return curso;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
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
		Aluno other = (Aluno) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

}
