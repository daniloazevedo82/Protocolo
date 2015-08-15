package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.AlunoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;
import br.edu.ifba.plugin.protocolo.visao.ICadastroAluno;

public class ModeloAluno {

	private AlunoDAO alunoDAO = new AlunoDAO();
	private ICadastroAluno cadastroAluno = null;
	
	public void setAlunoDAO(AlunoDAO alunoDAO) {this.alunoDAO = alunoDAO;}
	public void setCadastroAluno(ICadastroAluno cadastroAluno) {this.cadastroAluno = cadastroAluno;}
	
	public void listarAluno(){
		List<Aluno> listaAluno = alunoDAO.getListagemAluno();
		cadastroAluno.setListaAluno(listaAluno);
	}
	
	public void salvarEditarAluno(){
		Aluno aluno = cadastroAluno.getAluno();
		aluno.setCategoria(cadastroAluno.getCategoria());
		aluno.setSexo(cadastroAluno.getSexo());
		
		if(aluno.getId() == null){
			alunoDAO.saveAluno(aluno);
		} else {
			alunoDAO.editAluno(aluno);
		}
	}
	
	public void deleteAluno(){
		alunoDAO.deleteAluno(cadastroAluno.getAluno());
	}
	
}
