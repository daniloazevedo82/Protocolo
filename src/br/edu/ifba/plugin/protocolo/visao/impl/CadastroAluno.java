package br.edu.ifba.plugin.protocolo.visao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;
import br.edu.ifba.plugin.protocolo.bd.beans.Curso;
import br.edu.ifba.plugin.protocolo.bd.enumeration.CategoriaEnum;
import br.edu.ifba.plugin.protocolo.bd.enumeration.SexoEnum;
import br.edu.ifba.plugin.protocolo.controle.ControleAluno;
import br.edu.ifba.plugin.protocolo.modelo.ModeloAluno;
import br.edu.ifba.plugin.protocolo.modelo.ModeloCurso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroAluno;

@ManagedBean(name = "cadastroAluno")
@ViewScoped
public class CadastroAluno implements ICadastroAluno {

	private Aluno aluno;
	private List<Aluno> listaAluno = new ArrayList<Aluno>();
	private List<Curso> listaCurso;
	private CategoriaEnum categoria;
	private SexoEnum sexo;
	
	boolean modal = false;
	
	public boolean exibirModal(){
		return modal;
	}
	
	public void prepararNovo(){
		this.aluno = new Aluno();
		this.sexo = null;
		this.categoria = null;
		carregaListaCurso();
		modal = true;
	}
	
	public void prepararEditar(Aluno aluno){
		this.aluno = aluno;
		sexo = aluno.getSexo();
		categoria = aluno.getCategoria();
		carregaListaCurso();
		modal = true;
	}
	
	public void fecharModal(){
		modal = false;
	}

	@Override
	public Aluno getAluno() {
		return aluno;
	}
	
	@Override
	public CategoriaEnum getCategoria() {
		return categoria;
	}
	
	public CategoriaEnum[] getCategoriaArray(){
		return CategoriaEnum.values();
	}
	
	public List<Aluno> getListaAluno() {
		return listaAluno;
	}
	
	public List<Curso> getListaCurso() {
		return listaCurso;
	}
	
	@Override
	public SexoEnum getSexo() {
		return sexo;
	}
	
	public SexoEnum[] getSexoArray(){
		return SexoEnum.values();
	}
	
	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}
	
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public void setCategoria(CategoriaEnum categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public void setListaAluno(List<Aluno> listaAluno) {
		this.listaAluno = listaAluno;
	}
	
	@Override
	public void setListaCurso(List<Curso> listaCurso) {
		this.listaCurso = listaCurso;
	}
	
	public void listarAluno(){
		defineControle().listarAluno();
	}
	
	public void salvarEditarAluno(){
		defineControle().salvarEditarAluno();
		listarAluno();
	}
	
	public void excluirAluno(){
		defineControle().deleteAluno();
		listarAluno();
	}
	
	public void carregaListaCurso(){
		ModeloCurso modeloCurso = new ModeloCurso();
		ControleAluno controleAluno = new ControleAluno();
		
		controleAluno.setModeloCurso(modeloCurso);
		controleAluno.setCadastroAluno(this);
		
		controleAluno.carregaListaCursoCombo();
	}
	
	private ControleAluno defineControle() {
		ModeloAluno modeloAluno = new ModeloAluno();
		ControleAluno controleAluno = new ControleAluno();
		
		controleAluno.setModeloAluno(modeloAluno);
		controleAluno.setCadastroAluno(this);
		
		return controleAluno;
	}

}
