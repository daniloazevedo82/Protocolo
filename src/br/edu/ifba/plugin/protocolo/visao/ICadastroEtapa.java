package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.bd.beans.Setor;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;

public interface ICadastroEtapa {

	public Etapa getEtapa();
	
	public void setEtapa(Etapa etapa);
	
	public List<Etapa> getListaEtapa();

	public void setListaEtapa(List<Etapa> listaEtapa);

	public void salvarEditarEtapa();
	
	public void excluirEtapa();
	
	public void setListaSetor(List<Setor> listaSetor);

	public void setListaTipoProcesso(List<TipoProcesso> listaTipoProcesso);
	
}
