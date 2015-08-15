package br.edu.ifba.plugin.protocolo.controle;

import br.edu.ifba.plugin.protocolo.modelo.ModeloEtapa;
import br.edu.ifba.plugin.protocolo.modelo.ModeloSetor;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoProcesso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;

public class ControleEtapa {

	private ICadastroEtapa cadastroEtapa;
	private ModeloEtapa modeloEtapa;
	private ModeloSetor modeloSetor;
	private ModeloTipoProcesso modeloTipoProcesso;
	
	public void setCadastroEtapa(ICadastroEtapa cadastroEtapa) {this.cadastroEtapa = cadastroEtapa;}
	public void setModeloEtapa(ModeloEtapa modeloEtapa) {this.modeloEtapa = modeloEtapa;}
	public void setModeloSetor(ModeloSetor modeloSetor) {this.modeloSetor = modeloSetor;}
	public void setModeloTipoProcesso(ModeloTipoProcesso modeloTipoProcesso) {this.modeloTipoProcesso = modeloTipoProcesso;}
	
	public void listarEtapas(){
		modeloEtapa.setCadastroEtapa(cadastroEtapa);
		modeloEtapa.listarEtapas();
	}
	
	public void salvarEditarEtapa(){
		modeloEtapa.setCadastroEtapa(cadastroEtapa);
		modeloEtapa.salvarEditarEtapa();
	}
	
	public void deleteEtapa(){
		modeloEtapa.setCadastroEtapa(cadastroEtapa);
		modeloEtapa.deleteEtapa();
	}
	
	public void carregaListaSetorCombo(){
		modeloSetor.setCadastroEtapa(cadastroEtapa);
		modeloSetor.listarSetorCombo();
	}
	
	public void carregaListaTipoProcessoCombo(){
		modeloTipoProcesso.setCadastroEtapa(cadastroEtapa);
		modeloTipoProcesso.listarTipoProcessoCombo();
	}
	
}
