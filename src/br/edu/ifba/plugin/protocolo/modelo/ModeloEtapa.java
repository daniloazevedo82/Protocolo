package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.EtapaDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;

public class ModeloEtapa {

	private EtapaDAO etapaDAO = new EtapaDAO();
	private ICadastroEtapa cadastroEtapa = null;
	
	public void setEtapaDAO(EtapaDAO etapaDAO) {this.etapaDAO = etapaDAO;}
	public void setCadastroEtapa(ICadastroEtapa cadastroEtapa) {this.cadastroEtapa = cadastroEtapa;}
	
	public void listarEtapas(){
		List<Etapa> listaEtapa = etapaDAO.getListagemEtapa();
		cadastroEtapa.setListaEtapa(listaEtapa);
	}
	
	public void salvarEditarEtapa(){
		Etapa etapa = cadastroEtapa.getEtapa();
		if(etapa.getId() == null){
			etapaDAO.saveEtapa(etapa);
		} else {
			etapaDAO.editEtapa(etapa);
		}
	}
	
	public void deleteEtapa(){
		etapaDAO.deleteEtapa(cadastroEtapa.getEtapa());
	}
	
}
