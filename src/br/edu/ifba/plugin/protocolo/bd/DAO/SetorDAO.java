package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Setor;

public class SetorDAO extends DAO {
	
	public List<Setor> getListagemSetor(){
		List<Setor> listaSetor = new ArrayList<Setor>();
		
		TypedQuery<Setor> query = em.createQuery("SELECT s "
												+ "FROM Setor s "
												+ "ORDER BY s.nome asc", Setor.class);

		try {
			listaSetor = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		
		return listaSetor;
	}

}
