package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;

public class DocumentoRequerimentoDAO extends DAO{

	public List<DocumentoRequerimento> getListagemDocumentosProcessoByProcessoRequerimento(ProcessoRequerimentoAcademico processoRequerimentoAcademico){
		List<DocumentoRequerimento> listaDocumentoRequerimento = new ArrayList<DocumentoRequerimento>();
		
		TypedQuery<DocumentoRequerimento> query = em.createQuery("SELECT dr "
												+ "FROM DocumentoRequerimento dr "
												+ "LEFT JOIN FETCH dr.processoRequerimentoAcademico pr "
												+ "LEFT JOIN FETCH dr.arquivo a "
												+ "WHERE pr.id = " + processoRequerimentoAcademico.getId()
												+ " ORDER BY dr.id asc", DocumentoRequerimento.class);
		
		try {
			listaDocumentoRequerimento = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
//			em.close();
		}
		
		return listaDocumentoRequerimento;
	}
	
}