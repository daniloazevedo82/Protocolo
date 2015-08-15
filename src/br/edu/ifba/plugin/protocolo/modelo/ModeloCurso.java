package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.CursoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.Curso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroAluno;

public class ModeloCurso {

	private CursoDAO cursoDAO = new CursoDAO();
	private ICadastroAluno cadastroAluno;
	
	public void setCursoDAO(CursoDAO cursoDAO) {this.cursoDAO = cursoDAO;}
	public void setCadastroAluno(ICadastroAluno cadastroAluno) {this.cadastroAluno = cadastroAluno;}
	
	public void listarCursoCombo(){
		List<Curso> listaCurso = cursoDAO.getListagemCurso();
		cadastroAluno.setListaCurso(listaCurso);
	}
	
}
