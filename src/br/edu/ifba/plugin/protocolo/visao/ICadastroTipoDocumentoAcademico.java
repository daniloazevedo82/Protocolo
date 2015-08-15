package br.edu.ifba.plugin.protocolo.visao;

import java.util.List;

import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;

public interface ICadastroTipoDocumentoAcademico {

	public TipoDocumentoAcademico getTipoDocumentoAcademico();
	
	public void setTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico);

	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico);

	public List<TipoDocumentoAcademico> getListaTipoDocumentoAcademico();
	
}
