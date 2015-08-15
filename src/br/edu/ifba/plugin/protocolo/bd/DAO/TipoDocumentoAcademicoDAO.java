package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;

public class TipoDocumentoAcademicoDAO extends DAO {

	public List<TipoDocumentoAcademico> getListagemTipoDocumentoAcademico(){
		List<TipoDocumentoAcademico> listaTipoDocumentoAcademico = new ArrayList<TipoDocumentoAcademico>();
		
		TypedQuery<TipoDocumentoAcademico> query = em.createQuery("SELECT tda "
																+ "FROM TipoDocumentoAcademico tda "
																+ "ORDER BY tda.nome asc", TipoDocumentoAcademico.class);

		try {
			listaTipoDocumentoAcademico = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		
		return listaTipoDocumentoAcademico;
	}
	
	public void saveTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico){
		try {
			iniciarTransacao();
			em.persist(tipoDocumentoAcademico);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void editTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico){
		try {
			iniciarTransacao();
			em.merge(tipoDocumentoAcademico);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
	public void deleteTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico){
		try {
			iniciarTransacao();
			TipoDocumentoAcademico tipoDocumentoAcademicoExclusao = em.merge(tipoDocumentoAcademico);
			em.remove(tipoDocumentoAcademicoExclusao);
			commitTransacao();
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
}
