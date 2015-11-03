package br.edu.ifba.plugin.protocolo.controle;

import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.IAcompanhamentoSolicitacoes;
import br.edu.ifba.plugin.protocolo.visao.ICadastroRequerimento;
import br.edu.ifba.plugin.protocolo.visao.ICadastroTipoDocumentoAcademico;

public class ControleTipoDocumentoAcademico {

	private ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico;
	private ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico;
	private ICadastroRequerimento cadastroRequerimento;
	private IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes;
	
	public void setCadastroTipoDocumentoAcademico(ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico) {this.cadastroTipoDocumentoAcademico = cadastroTipoDocumentoAcademico;}
	public void setModeloTipoDocumentoAcademico(ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico) {this.modeloTipoDocumentoAcademico = modeloTipoDocumentoAcademico;}
	public void setCadastroRequerimento(ICadastroRequerimento cadastroRequerimento) {this.cadastroRequerimento = cadastroRequerimento;}
	public void setAcompanhamentoSolicitacoes(IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes) {this.acompanhamentoSolicitacoes = acompanhamentoSolicitacoes;}
	
	public void listarTipoDocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.listarTipoDocumentoAcademico();
	}
	
	public void salvarEditarTipodocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.salvarEditarTipoDocumentoAcademico();
	}
	
	public void deleteTipoDocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.deleteTipoDocumentoAcademico();
	}
	
	public void listarTipoDocumentoAcademicoCombo(){
		modeloTipoDocumentoAcademico.setCadastroRequerimento(cadastroRequerimento);
		modeloTipoDocumentoAcademico.listarTipoDocumentoAcademicoCombo();
	}
	
	public void listarTipoDocumentoAcademicoComboAcompanhamento(){
		modeloTipoDocumentoAcademico.setAcompanhamentoSolicitacoes(acompanhamentoSolicitacoes);
		modeloTipoDocumentoAcademico.listarTipoDocumentoAcademicoComboAcompanhamento();
	}
	
}
