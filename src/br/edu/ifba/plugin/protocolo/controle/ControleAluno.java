package br.edu.ifba.plugin.protocolo.controle;

import br.edu.ifba.plugin.protocolo.modelo.ModeloAluno;
import br.edu.ifba.plugin.protocolo.modelo.ModeloCurso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroAluno;

public class ControleAluno {

	private ICadastroAluno cadastroAluno;
	private ModeloAluno modeloAluno;
	private ModeloCurso modeloCurso;
	
	public void setCadastroAluno(ICadastroAluno cadastroAluno) {this.cadastroAluno = cadastroAluno;}
	public void setModeloAluno(ModeloAluno modeloAluno) {this.modeloAluno = modeloAluno;}
	public void setModeloCurso(ModeloCurso modeloCurso) {this.modeloCurso = modeloCurso;}
	
	public void listarAluno(){
		modeloAluno.setCadastroAluno(cadastroAluno);
		modeloAluno.listarAluno();
	}
	
	public void salvarEditarAluno(){
		modeloAluno.setCadastroAluno(cadastroAluno);
		modeloAluno.salvarEditarAluno();
	}
	
	public void deleteAluno(){
		modeloAluno.setCadastroAluno(cadastroAluno);
		modeloAluno.deleteAluno();
	}
	
	public void carregaListaCursoCombo(){
		modeloCurso.setCadastroAluno(cadastroAluno);
		modeloCurso.listarCursoCombo();
	}
	
}
