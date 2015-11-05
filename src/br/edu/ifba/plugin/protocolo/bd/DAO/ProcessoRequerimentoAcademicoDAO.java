package br.edu.ifba.plugin.protocolo.bd.DAO;

import java.sql.Timestamp;
import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.Historico;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;

public class ProcessoRequerimentoAcademicoDAO extends DAO {

	public ProcessoRequerimentoAcademico saveProcessoRequerimentoAcademico(ProcessoRequerimentoAcademico processoRequerimentoAcademico, 
																		List<DocumentoRequerimento> listaDocumento){
		
		try {
			iniciarTransacao();
			if(processoRequerimentoAcademico.getId() == null){
				em.persist(processoRequerimentoAcademico);
			} else {
				em.merge(processoRequerimentoAcademico);
			}
			
			if(listaDocumento != null && !listaDocumento.isEmpty()){
				for(DocumentoRequerimento item : listaDocumento){
					if(item.getId() == null){
						item.setProcessoRequerimentoAcademico(processoRequerimentoAcademico);
						item.setData(processoRequerimentoAcademico.getData());
						em.persist(item);
					}
				}
			}
			
			commitTransacao();
			
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
		
		return processoRequerimentoAcademico;
	}
	
	public void saveListaEtapasProcesso(List<EtapasProcesso> listaEtapasProcesso){
		try {
			iniciarTransacao();
			
			for(EtapasProcesso item : listaEtapasProcesso){
				em.persist(item);
				
				if(item.getEtapa().getPrimeiraEtapa()){
					Historico historico = new Historico();
					historico.setEtapasProcesso(item);
					historico.setDataHoraInicio(new Timestamp(System.currentTimeMillis()));
					historico.setDescricao("Processo " + item.getProcessoRequerimentoAcademico().getTipoProcesso().getNome() + " criado.");
					historico.setObservacao(item.getProcessoRequerimentoAcademico().getObservacoes());
					if(item.getParecer() != null){
						historico.setParecer(item.getParecer());
					}
					em.persist(historico);
				}
			}
			
			commitTransacao();
			
		} catch (Exception e) {
			rollbackTransacao();
			e.printStackTrace();
		}
	}
	
}
