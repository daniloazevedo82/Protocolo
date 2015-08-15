package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;

public class AlunoDAO extends DAO{

	public List<Aluno> getListagemAluno(){
		List<Aluno> listaAluno = new ArrayList<Aluno>();
		
		TypedQuery<Aluno> query = em.createQuery("SELECT a "
												+ "FROM Aluno a "
												+ "ORDER BY a.nome asc", Aluno.class);

		try {
			listaAluno = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		return listaAluno;
	}
	
	public void saveAluno(Aluno aluno){
		try {
			iniciarTransacao();
			em.persist(aluno);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void editAluno(Aluno aluno){
		try {
			iniciarTransacao();
			em.merge(aluno);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void deleteAluno(Aluno aluno){
		try {
			iniciarTransacao();
			Aluno alunoExclusao = em.merge(aluno);
			em.remove(alunoExclusao);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
}
