package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;

public class EtapaDAO extends DAO {

	public List<Etapa> getListagemEtapa(){
		List<Etapa> listaEtapa = new ArrayList<Etapa>();
		
		TypedQuery<Etapa> query = em.createQuery("SELECT e "
												+ "FROM Etapa e "
												+ "WHERE e.tipoProcesso.id = 7 "
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
	
	public Etapa findEtapaInicialByTipoProcesso(TipoProcesso tipoProcesso){
		Etapa etapa = new Etapa();
		
		TypedQuery<Etapa> query = em.createQuery("SELECT e "
												+ "FROM Etapa e "
												+ "WHERE e.tipoProcesso = :tipoprocesso "
												+ "AND e.nrSequencia = 1" , Etapa.class);
		
		query.setParameter("tipoprocesso", tipoProcesso);

		try {
			etapa = query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		return etapa;
	}
	
	public List<Etapa> findEtapasByTipoProcesso(TipoProcesso tipoProcesso){
		List<Etapa> listaEtapa = new ArrayList<Etapa>();
		
		TypedQuery<Etapa> query = em.createQuery("SELECT e "
												+ "FROM Etapa e "
												+ "WHERE e.tipoProcesso = :tipoprocesso "
												+ "ORDER BY e.nrSequencia asc", Etapa.class);
		
		query.setParameter("tipoprocesso", tipoProcesso);

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
	
}
