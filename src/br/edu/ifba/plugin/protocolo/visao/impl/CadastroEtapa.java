package br.edu.ifba.plugin.protocolo.visao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.bd.beans.Setor;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;
import br.edu.ifba.plugin.protocolo.controle.ControleEtapa;
import br.edu.ifba.plugin.protocolo.modelo.ModeloEtapa;
import br.edu.ifba.plugin.protocolo.modelo.ModeloSetor;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoProcesso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;

@ManagedBean(name = "cadastroEtapa")
@ViewScoped
public class CadastroEtapa implements ICadastroEtapa{

	//RequestContext.getCurrentInstance().execute("alert('eu')");
	
	private Etapa etapa;
	private List<Etapa> listaEtapa = new ArrayList<Etapa>();
	private List<Setor> listaSetor;
	private List<TipoProcesso> listaTipoProcesso;
	
	boolean modal = false;
	
	public boolean exibirModal(){
		return modal;
	}
	
	public void prepararNovo(){
		this.etapa = new Etapa();
		carregaListaSetor();
		carregaListaTipoProcesso();
		modal = true;
	}
	
	public void prepararEditar(Etapa etapa){
		this.etapa = etapa;
		carregaListaSetor();
		carregaListaTipoProcesso();
		modal = true;
	}
	
	public void fecharModal(){
		modal = false;
	}
	
	@Override
	public Etapa getEtapa() {
		return etapa;
	}
	@Override
	public List<Etapa> getListaEtapa() {
		return listaEtapa;
	}
	
	public List<Setor> getListaSetor() {
		return listaSetor;
	}
	
	public List<TipoProcesso> getListaTipoProcesso() {
		return listaTipoProcesso;
	}

	@Override
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	
	@Override
	public void setListaEtapa(List<Etapa> listaEtapa) {
		this.listaEtapa = listaEtapa;
	}
	@Override
	public void setListaSetor(List<Setor> listaSetor) {
		this.listaSetor = listaSetor;
	}
	
	@Override
	public void setListaTipoProcesso(List<TipoProcesso> listaTipoProcesso) {
		this.listaTipoProcesso = listaTipoProcesso;
	}

	public void listarEtapas(){
		defineControle().listarEtapas();
	}

	@Override
	public void salvarEditarEtapa() {
		defineControle().salvarEditarEtapa();
		listarEtapas();
	}

	@Override
	public void excluirEtapa() {
		defineControle().deleteEtapa();
		listarEtapas();
	}
	
	public void carregaListaSetor(){
		ModeloSetor modeloSetor = new ModeloSetor();
		ControleEtapa controleEtapa = new ControleEtapa();
		
		controleEtapa.setModeloSetor(modeloSetor);
		controleEtapa.setCadastroEtapa(this);
		
		controleEtapa.carregaListaSetorCombo();
	}
	
	public void carregaListaTipoProcesso(){
		ModeloTipoProcesso modeloTipoProcesso = new ModeloTipoProcesso();
		ControleEtapa controleEtapa = new ControleEtapa();
		
		controleEtapa.setModeloTipoProcesso(modeloTipoProcesso);
		controleEtapa.setCadastroEtapa(this);
		
		controleEtapa.carregaListaTipoProcessoCombo();
	}
	
	private ControleEtapa defineControle() {
		ModeloEtapa modeloEtapa = new ModeloEtapa();
		ControleEtapa controleEtapa = new ControleEtapa();
		
		controleEtapa.setModeloEtapa(modeloEtapa);
		controleEtapa.setCadastroEtapa(this);
		
		return controleEtapa;
	}
	
	public void logar(){  
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
    } 

}
