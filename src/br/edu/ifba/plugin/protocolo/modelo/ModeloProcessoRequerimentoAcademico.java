package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.ProcessoRequerimentoAcademicoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.IAcompanhamentoSolicitacoes;
import br.edu.ifba.plugin.protocolo.visao.ICadastroRequerimento;

public class ModeloProcessoRequerimentoAcademico {

	private ProcessoRequerimentoAcademicoDAO processoRequerimentoAcademicoDAO = new ProcessoRequerimentoAcademicoDAO();
	private ICadastroRequerimento cadastroRequerimento;
	private IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes;
	
	public void setProcessoRequerimentoAcademicoDAO(ProcessoRequerimentoAcademicoDAO processoRequerimentoAcademicoDAO) {
		this.processoRequerimentoAcademicoDAO = processoRequerimentoAcademicoDAO;
	}
	public void setCadastroRequerimento(ICadastroRequerimento cadastroRequerimento) {
		this.cadastroRequerimento = cadastroRequerimento;
	}
	public void setAcompanhamentoSolicitacoes(IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes) {
		this.acompanhamentoSolicitacoes = acompanhamentoSolicitacoes;
	}
	
	public void salvarProcessoRequerimento(){
		ProcessoRequerimentoAcademico processoRequerimentoAcademico = cadastroRequerimento.getProcessoRequerimentoAcademico();
		List<DocumentoRequerimento> listaDocumento = cadastroRequerimento.getListaDocumentoRequerimento();
		cadastroRequerimento.setProcessoSalvo(processoRequerimentoAcademicoDAO.saveProcessoRequerimentoAcademico(processoRequerimentoAcademico, listaDocumento));
	}
	
	public void salvarEtapasProcesso(List<EtapasProcesso> listaEtapasProcesso){
		processoRequerimentoAcademicoDAO.saveListaEtapasProcesso(listaEtapasProcesso);
	}
	
	public void salvarAcompanhamento(){
		ProcessoRequerimentoAcademico processoRequerimentoAcademico = acompanhamentoSolicitacoes.getProcessoAtual();
		List<DocumentoRequerimento> listaDocumento = acompanhamentoSolicitacoes.getListaDocumentoRequerimento();
		acompanhamentoSolicitacoes.setProcessoAtual(processoRequerimentoAcademicoDAO.saveProcessoRequerimentoAcademico(processoRequerimentoAcademico, listaDocumento));
	}
	
}
