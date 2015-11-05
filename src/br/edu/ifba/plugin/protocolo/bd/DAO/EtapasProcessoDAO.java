package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.enumeration.StatusEnum;

public class EtapasProcessoDAO extends DAO{

	public List<EtapasProcesso> getListagemEtapasProcesso(Integer idusuariologado, Integer idsetorusuariologado){
		List<EtapasProcesso> listaEtapasProcesso = new ArrayList<EtapasProcesso>();
		
		TypedQuery<EtapasProcesso> query = em.createQuery("SELECT ep "
												+ "FROM EtapasProcesso ep "
												+ "LEFT JOIN FETCH ep.etapa e "
												+ "LEFT JOIN FETCH e.setor s "
												+ "LEFT JOIN FETCH ep.processoRequerimentoAcademico pr "
												+ "LEFT JOIN FETCH pr.tipoProcesso tp "
												+ "LEFT JOIN FETCH pr.aluno al "
												+ "LEFT JOIN FETCH al.curso c "
												+ "WHERE s.id = " + idsetorusuariologado
												+ " AND ep.status = :status "
												+ " ORDER BY pr.data desc, pr.id desc", EtapasProcesso.class);
		
		query.setParameter("status", StatusEnum.EM_ESPERA);

		try {
			listaEtapasProcesso = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
//			em.close();
		}
		
		return listaEtapasProcesso;
	}
	
	public List<EtapasProcesso> getListagemEtapasProcessoByProcessoRequerimento(ProcessoRequerimentoAcademico processoRequerimentoAcademico){
		List<EtapasProcesso> listaEtapasProcesso = new ArrayList<EtapasProcesso>();
		
		TypedQuery<EtapasProcesso> query = em.createQuery("SELECT ep "
												+ "FROM EtapasProcesso ep "
												+ "LEFT JOIN FETCH ep.etapa e "
												+ "LEFT JOIN FETCH e.setor s "
												+ "LEFT JOIN FETCH ep.processoRequerimentoAcademico pr "
												+ "LEFT JOIN FETCH pr.tipoProcesso tp "
												+ "LEFT JOIN FETCH pr.aluno al "
												+ "LEFT JOIN FETCH al.curso c "
												+ "WHERE pr.id = " + processoRequerimentoAcademico.getId()
												+ " ORDER BY e.nrSequencia asc", EtapasProcesso.class);
		
		try {
			listaEtapasProcesso = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
//			em.close();
		}
		
		return listaEtapasProcesso;
	}
	
	public EtapasProcesso getProximaEtapa(EtapasProcesso etapasProcesso){
		EtapasProcesso proximaEtapa = new EtapasProcesso();
		
		TypedQuery<EtapasProcesso> query = em.createQuery("SELECT ep "
												+ "FROM EtapasProcesso ep "
												+ "LEFT JOIN FETCH ep.etapa e "
												+ "LEFT JOIN FETCH e.setor s "
												+ "LEFT JOIN FETCH ep.processoRequerimentoAcademico pr "
												+ "LEFT JOIN FETCH pr.tipoProcesso tp "
												+ "LEFT JOIN FETCH pr.aluno al "
												+ "LEFT JOIN FETCH al.curso c "
												+ "WHERE e.nrSequencia = :nrsequencia "
												+ "AND pr.id = :idprocesso ", EtapasProcesso.class);
		
		query.setParameter("nrsequencia", (etapasProcesso.getEtapa().getNrSequencia() + 1));
		query.setParameter("idprocesso", etapasProcesso.getProcessoRequerimentoAcademico().getId());
		
		try {
			proximaEtapa = query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
//			em.close();
		}
		
		return proximaEtapa;
	}
	
	public EtapasProcesso saveEtapasProcesso(EtapasProcesso etapasProcesso){
		try {
			iniciarTransacao();
			
			if(etapasProcesso.getId() == null){
				em.persist(etapasProcesso);
			} else {
				em.merge(etapasProcesso);
			}
			
			if(!etapasProcesso.getEtapa().getUltimaEtapa() && etapasProcesso.getStatus().equals(StatusEnum.CONCLUIDO)){
				EtapasProcesso proximaEtapa = this.getProximaEtapa(etapasProcesso);
				proximaEtapa.setStatus(StatusEnum.EM_ESPERA);
				
				em.merge(proximaEtapa);
			}
			
			commitTransacao();
			
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
		
		return etapasProcesso;
	}
	
}