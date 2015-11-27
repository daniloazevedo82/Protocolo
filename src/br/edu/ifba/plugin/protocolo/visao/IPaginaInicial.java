package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;

public interface IPaginaInicial {

	public void setListaEtapasProcessoEmEspera(List<EtapasProcesso> listaEtapasProcessoEmEspera);

	public void setListaEtapasProcessoNaoIniciados(List<EtapasProcesso> listaEtapasProcessoNaoIniciados);

}
