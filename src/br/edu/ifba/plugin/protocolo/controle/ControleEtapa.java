package br.edu.ifba.plugin.protocolo.controle;

import br.edu.ifba.plugin.protocolo.modelo.ModeloEtapa;
import br.edu.ifba.plugin.protocolo.modelo.ModeloSetor;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoProcesso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;
import br.edu.ifba.plugin.protocolo.visao.ICadastroRequerimento;

public class ControleEtapa {

	private ICadastroEtapa cadastroEtapa;
	private ModeloEtapa modeloEtapa;
	private ModeloSetor modeloSetor;
	private ModeloTipoProcesso modeloTipoProcesso;
	private ICadastroRequerimento cadastroRequerimento;
	
	public void setCadastroEtapa(ICadastroEtapa cadastroEtapa) {this.cadastroEtapa = cadastroEtapa;}
	public void setModeloEtapa(ModeloEtapa modeloEtapa) {this.modeloEtapa = modeloEtapa;}
	public void setModeloSetor(ModeloSetor modeloSetor) {this.modeloSetor = modeloSetor;}
	public void setModeloTipoProcesso(ModeloTipoProcesso modeloTipoProcesso) {this.modeloTipoProcesso = modeloTipoProcesso;}
	public void setCadastroRequerimento(ICadastroRequerimento cadastroRequerimento) {this.cadastroRequerimento = cadastroRequerimento;}
	
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
	
	public void carregaEtapaCadastroRequerimento(){
		modeloEtapa.setCadastroRequerimento(cadastroRequerimento);
		modeloEtapa.carregaEtapaCadastroRequerimento();
	}
	
	public void carregaListaEtapaCadastroRequerimento(){
		modeloEtapa.setCadastroRequerimento(cadastroRequerimento);
		modeloEtapa.carregaListaEtapaCadastroRequerimento();
	}
	
}
