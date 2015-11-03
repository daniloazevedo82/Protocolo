package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Historico;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;

public class HistoricoDAO extends DAO{

	public List<Historico> getListagemHistoricoByProcessoRequerimento(ProcessoRequerimentoAcademico processoRequerimentoAcademico){
		List<Historico> listaHistorico = new ArrayList<Historico>();
		
		TypedQuery<Historico> query = em.createQuery("SELECT h "
												+ "FROM Historico h "
												+ "LEFT JOIN FETCH h.etapasProcesso ep "
												+ "LEFT JOIN FETCH ep.processoRequerimentoAcademico pr "
												+ "WHERE pr.id = " + processoRequerimentoAcademico.getId()
												+ " ORDER BY h.id desc", Historico.class);
		
		try {
			listaHistorico = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
//			em.close();
		}
		
		return listaHistorico;
	}
	
	public void saveHistorico(Historico historico){
		try {
			iniciarTransacao();
			
			em.persist(historico);
			
			commitTransacao();
			
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
		
	}
	
}