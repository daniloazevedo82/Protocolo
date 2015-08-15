package br.edu.ifba.plugin.protocolo.bd.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.ifba.plugin.protocolo.bd.enumeration.CategoriaEnum;
import br.edu.ifba.plugin.protocolo.bd.enumeration.SexoEnum;

@Entity
@Table(name="pessoa", schema="administrativo")
@Inheritance(strategy=InheritanceType.JOINED)
@SequenceGenerator(name="sq_pessoa", sequenceName="sq_pessoa", schema="administrativo", allocationSize=1)
public class Pessoa {

	protected Integer id;
	protected String rg;
	protected String cpf;
	protected String nome;
	protected SexoEnum sexo;
	protected Date datanascimento;
	protected String endereco;
	protected String telefone;
	protected String email;
	protected CategoriaEnum categoria;
	
	public Pessoa() {
	}
	
	public Pessoa(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_pessoa")
	public Integer getId() {
		return id;
	}
	public String getRg() {
		return rg;
	}
	public String getCpf() {
		return cpf;
	}
	public String getNome() {
		return nome;
	}
	public SexoEnum getSexo() {
		return sexo;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="datanascimento")
	public Date getDatanascimento() {
		return datanascimento;
	}
	public String getEndereco() {
		return endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public String getEmail() {
		return email;
	}
	@Column(name="idcategoria")
	public CategoriaEnum getCategoria() {
		return categoria;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}
	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCategoria(CategoriaEnum categoria) {
		this.categoria = categoria;
	}
	@Override
	public String toString() {
		return nome;
	}
	
	
}
