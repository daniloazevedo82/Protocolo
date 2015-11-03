package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;

public interface ICadastroRequerimento {

	public void setListaTipoProcesso(List<TipoProcesso> listaTipoProcesso);

	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico);
	
	public void setEtapa(Etapa etapa);

	public ProcessoRequerimentoAcademico getProcessoRequerimentoAcademico();

	public void setProcessoSalvo(ProcessoRequerimentoAcademico processoSalvo);

	public List<DocumentoRequerimento> getListaDocumentoRequerimento();

	public void setListaEtapas(List<Etapa> listaEtapas);

	public ProcessoRequerimentoAcademico getProcessoSalvo();
	
}
