package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Curso;

public class CursoDAO extends DAO {

	public List<Curso> getListagemCurso(){
		List<Curso> listaCurso = new ArrayList<Curso>();
		
		TypedQuery<Curso> query = em.createQuery("SELECT c "
												+ "FROM Curso c "
												+ "ORDER BY c.nome asc", Curso.class);

		try {
			listaCurso = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		
		return listaCurso;
	}
	
}
