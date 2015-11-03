package br.edu.ifba.plugin.protocolo.controle;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.modelo.ModeloProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoProcesso;
import br.edu.ifba.plugin.protocolo.visao.IAcompanhamentoSolicitacoes;
import br.edu.ifba.plugin.protocolo.visao.ICadastroRequerimento;

public class ControleRequerimentoAcademico {

	private ModeloTipoProcesso modeloTipoProcesso;
	private ICadastroRequerimento cadastroRequerimento;
	private ModeloProcessoRequerimentoAcademico modeloProcessoRequerimentoAcademico;
	private IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes;
	
	public void setModeloTipoProcesso(ModeloTipoProcesso modeloTipoProcesso) {this.modeloTipoProcesso = modeloTipoProcesso;}
	public void setCadastroRequerimento(ICadastroRequerimento cadastroRequerimento) {this.cadastroRequerimento = cadastroRequerimento;}
	public void setModeloProcessoRequerimentoAcademico(ModeloProcessoRequerimentoAcademico modeloProcessoRequerimentoAcademico) {this.modeloProcessoRequerimentoAcademico = modeloProcessoRequerimentoAcademico;}
	public void setAcompanhamentoSolicitacoes(IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes) {this.acompanhamentoSolicitacoes = acompanhamentoSolicitacoes;}
	
	public void carregaListaTipoProcessoComboRequerimento(){
		modeloTipoProcesso.setCadastroRequerimento(cadastroRequerimento);
		modeloTipoProcesso.listarTipoProcessoComboRequerimento();
	}
	
	public void salvarRequerimento(){
		modeloProcessoRequerimentoAcademico.setCadastroRequerimento(cadastroRequerimento);
		modeloProcessoRequerimentoAcademico.salvarProcessoRequerimento();
	}
	
	public void salvarEtapasProcesso(List<EtapasProcesso> listaEtapasProcesso){
		modeloProcessoRequerimentoAcademico.salvarEtapasProcesso(listaEtapasProcesso);
	}
	
	public void salvarAcompanhamento(){
		modeloProcessoRequerimentoAcademico.setAcompanhamentoSolicitacoes(acompanhamentoSolicitacoes);
		modeloProcessoRequerimentoAcademico.salvarAcompanhamento();
	}
	
}
