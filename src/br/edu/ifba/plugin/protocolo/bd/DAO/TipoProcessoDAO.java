package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;

public class TipoProcessoDAO extends DAO{

	public List<TipoProcesso> getListagemTipoProcesso(){
		List<TipoProcesso> listaTipoProcesso = new ArrayList<TipoProcesso>();
		
		TypedQuery<TipoProcesso> query = em.createQuery("SELECT tp "
												+ "FROM TipoProcesso tp "
												+ "ORDER BY tp.nome asc", TipoProcesso.class);

		try {
			listaTipoProcesso = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
		
		return listaTipoProcesso;
	}
	
}
