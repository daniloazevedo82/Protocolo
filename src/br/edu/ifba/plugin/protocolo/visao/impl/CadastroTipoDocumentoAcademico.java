package br.edu.ifba.plugin.protocolo.visao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.controle.ControleTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.ICadastroTipoDocumentoAcademico;

@ManagedBean(name = "cadastroTipoDocumentoAcademico")
@ViewScoped
public class CadastroTipoDocumentoAcademico implements ICadastroTipoDocumentoAcademico {

	private TipoDocumentoAcademico tipoDocumentoAcademico;
	private List<TipoDocumentoAcademico> listaTipoDocumentoAcademico = new ArrayList<TipoDocumentoAcademico>();
	
	boolean modal = false;
	
	public boolean exibirModal(){
		return modal;
	}
	
	public void prepararNovo(){
		this.tipoDocumentoAcademico = new TipoDocumentoAcademico();
		modal = true;
	}
	
	public void prepararEditar(TipoDocumentoAcademico tipoDocumentoAcademico){
		this.tipoDocumentoAcademico = tipoDocumentoAcademico;
		modal = true;
	}
	
	public void fecharModal(){
		modal = false;
	}

	@Override
	public TipoDocumentoAcademico getTipoDocumentoAcademico() {
		return tipoDocumentoAcademico;
	}
	@Override
	public List<TipoDocumentoAcademico> getListaTipoDocumentoAcademico() {
		return listaTipoDocumentoAcademico;
	}
	
	@Override
	public void setTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico) {
		this.tipoDocumentoAcademico = tipoDocumentoAcademico;
	}
	@Override
	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico) {
		this.listaTipoDocumentoAcademico = listaTipoDocumentoAcademico;
	}
	
	public void listarTipoDocumentoAcademico(){
		defineControle().listarTipoDocumentoAcademico();
	}
	
	public void salvarEditarTipoDocumentoAcademico(){
		defineControle().salvarEditarTipodocumentoAcademico();
		listarTipoDocumentoAcademico();
	}
	
	public void excluirTipoDocumentoAcademico(){
		defineControle().deleteTipoDocumentoAcademico();
		listarTipoDocumentoAcademico();
	}
	
	private ControleTipoDocumentoAcademico defineControle() {
		ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico = new ModeloTipoDocumentoAcademico();
		ControleTipoDocumentoAcademico controleTipoDocumentoAcademico = new ControleTipoDocumentoAcademico();
		
		controleTipoDocumentoAcademico.setModeloTipoDocumentoAcademico(modeloTipoDocumentoAcademico);
		controleTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(this);
		
		return controleTipoDocumentoAcademico;
	}
	
}
