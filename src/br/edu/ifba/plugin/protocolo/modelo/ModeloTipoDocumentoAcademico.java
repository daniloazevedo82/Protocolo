package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.TipoDocumentoAcademicoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.IAcompanhamentoSolicitacoes;
import br.edu.ifba.plugin.protocolo.visao.ICadastroRequerimento;
import br.edu.ifba.plugin.protocolo.visao.ICadastroTipoDocumentoAcademico;

public class ModeloTipoDocumentoAcademico {

	private TipoDocumentoAcademicoDAO tipoDocumentoAcademicoDAO = new TipoDocumentoAcademicoDAO();
	private ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico = null;
	private ICadastroRequerimento cadastroRequerimento;
	private IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes;
	
	public void setTipoDocumentoAcademicoDAO(TipoDocumentoAcademicoDAO tipoDocumentoAcademicoDAO) {this.tipoDocumentoAcademicoDAO = tipoDocumentoAcademicoDAO;}
	public void setCadastroTipoDocumentoAcademico(ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico) {this.cadastroTipoDocumentoAcademico = cadastroTipoDocumentoAcademico;}
	public void setCadastroRequerimento(ICadastroRequerimento cadastroRequerimento) {this.cadastroRequerimento = cadastroRequerimento;}
	public void setAcompanhamentoSolicitacoes(IAcompanhamentoSolicitacoes acompanhamentoSolicitacoes) {this.acompanhamentoSolicitacoes = acompanhamentoSolicitacoes;}
	
	public void listarTipoDocumentoAcademico(){
		List<TipoDocumentoAcademico> listaTipoDocumentoAcademico = tipoDocumentoAcademicoDAO.getListagemTipoDocumentoAcademico();
		cadastroTipoDocumentoAcademico.setListaTipoDocumentoAcademico(listaTipoDocumentoAcademico);
	}
	
	public void salvarEditarTipoDocumentoAcademico(){
		TipoDocumentoAcademico tipoDocumentoAcademico = cadastroTipoDocumentoAcademico.getTipoDocumentoAcademico();
		if(tipoDocumentoAcademico.getId() == null){
			tipoDocumentoAcademicoDAO.saveTipoDocumentoAcademico(tipoDocumentoAcademico);
		} else {
			tipoDocumentoAcademicoDAO.editTipoDocumentoAcademico(tipoDocumentoAcademico);
		}
	}
	
	public void deleteTipoDocumentoAcademico(){
		tipoDocumentoAcademicoDAO.deleteTipoDocumentoAcademico(cadastroTipoDocumentoAcademico.getTipoDocumentoAcademico());
	}
	
	public void listarTipoDocumentoAcademicoCombo(){
		List<TipoDocumentoAcademico> listaTipoDocumentoAcademico = tipoDocumentoAcademicoDAO.getListagemTipoDocumentoAcademico();
		cadastroRequerimento.setListaTipoDocumentoAcademico(listaTipoDocumentoAcademico);
	}
	
	public void listarTipoDocumentoAcademicoComboAcompanhamento(){
		List<TipoDocumentoAcademico> listaTipoDocumentoAcademico = tipoDocumentoAcademicoDAO.getListagemTipoDocumentoAcademico();
		acompanhamentoSolicitacoes.setListaTipoDocumentoAcademico(listaTipoDocumentoAcademico);
	}
	
}
