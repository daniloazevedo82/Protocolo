package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;

public interface IAcompanhamentoSolicitacoes {

	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico);

	public void setListaEtapasProcesso(List<EtapasProcesso> listaEtapasProcesso);

	public void setListaDocumentoRequerimentoAnterior(List<DocumentoRequerimento> listaDocumentoRequerimentoAnterior);

	public List<DocumentoRequerimento> getListaDocumentoRequerimento();

	public ProcessoRequerimentoAcademico getProcessoAtual();

	public void setProcessoAtual(ProcessoRequerimentoAcademico processoAtual);

}
