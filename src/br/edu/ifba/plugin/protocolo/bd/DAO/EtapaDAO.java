package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;

public class EtapaDAO extends DAO {

	public List<Etapa> getListagemEtapa(){
		List<Etapa> listaEtapa = new ArrayList<Etapa>();
		
		TypedQuery<Etapa> query = em.createQuery("SELECT e "
												+ "FROM Etapa e "
												+ "ORDER BY e.tipoProcesso.nome asc, e.nrSequencia asc", Etapa.class);

		try {
			listaEtapa = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		return listaEtapa;
	}
	
	public void saveEtapa(Etapa etapa){
		try {
			iniciarTransacao();
			em.persist(etapa);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void editEtapa(Etapa etapa){
		try {
			iniciarTransacao();
			em.merge(etapa);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void deleteEtapa(Etapa etapa){
		try {
			iniciarTransacao();
			Etapa etapaExclusao = em.merge(etapa);
			em.remove(etapaExclusao);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
}
