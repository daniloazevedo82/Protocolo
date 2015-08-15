package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.SetorDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.Setor;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;

public class ModeloSetor {

	private SetorDAO setorDAO = new SetorDAO();
	private ICadastroEtapa cadastroEtapa;
	
	public void setSetorDAO(SetorDAO setorDAO) {this.setorDAO = setorDAO;}
	public void setCadastroEtapa(ICadastroEtapa cadastroEtapa) {this.cadastroEtapa = cadastroEtapa;}
	
	public void listarSetorCombo(){
		List<Setor> listaSetor = setorDAO.getListagemSetor();
		cadastroEtapa.setListaSetor(listaSetor);
	}
	
}
