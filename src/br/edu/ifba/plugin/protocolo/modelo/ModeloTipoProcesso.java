package br.edu.ifba.plugin.protocolo.modelo;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.DAO.TipoProcessoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;
import br.edu.ifba.plugin.protocolo.visao.ICadastroEtapa;

public class ModeloTipoProcesso {

	private TipoProcessoDAO tipoProcessoDAO = new TipoProcessoDAO();
	private ICadastroEtapa cadastroEtapa;
	
	public void setTipoProcessoDAO(TipoProcessoDAO tipoProcessoDAO) {this.tipoProcessoDAO = tipoProcessoDAO;}
	public void setCadastroEtapa(ICadastroEtapa cadastroEtapa) {this.cadastroEtapa = cadastroEtapa;}
	
	public void listarTipoProcessoCombo(){
		List<TipoProcesso> listaTipoProcesso = tipoProcessoDAO.getListagemTipoProcesso();
		cadastroEtapa.setListaTipoProcesso(listaTipoProcesso);
	}
	
}
