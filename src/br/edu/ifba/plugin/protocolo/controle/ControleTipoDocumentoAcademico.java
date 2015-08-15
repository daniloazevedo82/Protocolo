package br.edu.ifba.plugin.protocolo.controle;

import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.ICadastroTipoDocumentoAcademico;

public class ControleTipoDocumentoAcademico {

	private ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico;
	private ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico;
	
	public void setCadastroTipoDocumentoAcademico(ICadastroTipoDocumentoAcademico cadastroTipoDocumentoAcademico) {this.cadastroTipoDocumentoAcademico = cadastroTipoDocumentoAcademico;}
	public void setModeloTipoDocumentoAcademico(ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico) {this.modeloTipoDocumentoAcademico = modeloTipoDocumentoAcademico;}
	
	public void listarTipoDocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.listarTipoDocumentoAcademico();
	}
	
	public void salvarEditarTipodocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.salvarEditarTipoDocumentoAcademico();
	}
	
	public void deleteTipoDocumentoAcademico(){
		modeloTipoDocumentoAcademico.setCadastroTipoDocumentoAcademico(cadastroTipoDocumentoAcademico);
		modeloTipoDocumentoAcademico.deleteTipoDocumentoAcademico();
	}
	
}
