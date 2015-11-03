package br.edu.ifba.plugin.protocolo.visao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sun.istack.internal.logging.Logger;

import br.edu.ifba.plugin.protocolo.bd.DAO.DocumentoRequerimentoDAO;
import br.edu.ifba.plugin.protocolo.bd.DAO.EtapasProcessoDAO;
import br.edu.ifba.plugin.protocolo.bd.DAO.HistoricoDAO;
import br.edu.ifba.plugin.protocolo.bd.beans.Arquivo;
import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.Historico;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.enumeration.ParecerEnum;
import br.edu.ifba.plugin.protocolo.bd.enumeration.StatusEnum;
import br.edu.ifba.plugin.protocolo.controle.ControleRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.controle.ControleTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.visao.IAcompanhamentoSolicitacoes;

@ManagedBean(name="acompanhamentoSolicitacoes")
@ViewScoped
public class AcompanhamentoSolicitacoes implements IAcompanhamentoSolicitacoes{

	private EtapasProcessoDAO processoDAO = new EtapasProcessoDAO();
	private DocumentoRequerimentoDAO requerimentoDAO = new DocumentoRequerimentoDAO();
	private HistoricoDAO historicoDAO = new HistoricoDAO();
	public void setProcessoDAO(EtapasProcessoDAO processoDAO) {this.processoDAO = processoDAO;}
	public void setRequerimentoDAO(DocumentoRequerimentoDAO requerimentoDAO) {this.requerimentoDAO = requerimentoDAO;}
	public void setHistoricoDAO(HistoricoDAO historicoDAO) {this.historicoDAO = historicoDAO;}
	
	//Arquivos
	private final String destino = "C:\\imgProtocolo\\";
	private Arquivo arquivo = new Arquivo();
	private List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
	private UploadedFile file;
	private List<UploadedFile> files;
	private DocumentoRequerimento documentoRequerimento = new DocumentoRequerimento();
	private List<DocumentoRequerimento> listaDocumentoRequerimento = new ArrayList<DocumentoRequerimento>();
	private List<DocumentoRequerimento> listaDocumentoRequerimentoAnterior = new ArrayList<DocumentoRequerimento>();
	private List<TipoDocumentoAcademico> listaTipoDocumentoAcademico;
	
	private EtapasProcesso etapasProcesso = new EtapasProcesso();
	private StatusEnum status;
	private ParecerEnum parecer;
	private ProcessoRequerimentoAcademico processoAtual = new ProcessoRequerimentoAcademico();
	private List<EtapasProcesso> listaEtapasProcesso = new ArrayList<EtapasProcesso>();
	private List<Historico> listaHistorico = new ArrayList<Historico>();
	private String observacaoAtual;
	
	boolean modalAcompanhamento = false;
	boolean modalAnexos = false;
	
	public boolean exibirModalAcompanhamento(){
		return modalAcompanhamento;
	}
	
	public void fecharModalAcompanhamento(){
		modalAcompanhamento = false;
	}
	
	public boolean exibirModalAnexos(){
		return modalAnexos;
	}
	
	public void fecharModalAnexos(){
		modalAnexos = false;
	}
	
	public void prepararModalAcompanhamento(EtapasProcesso etapas){
		setEtapasProcesso(etapas);
		carregarListaDocumento();
		listaEtapasProcesso = processoDAO.getListagemEtapasProcessoByProcessoRequerimento(etapasProcesso.getProcessoRequerimentoAcademico());
		listaDocumentoRequerimentoAnterior = requerimentoDAO.getListagemDocumentosProcessoByProcessoRequerimento(etapasProcesso.getProcessoRequerimentoAcademico());
		listaHistorico = historicoDAO.getListagemHistoricoByProcessoRequerimento(etapasProcesso.getProcessoRequerimentoAcademico());
		modalAcompanhamento = true;
	}
	
	public void carregarListaDocumento(){
		ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico = new ModeloTipoDocumentoAcademico();
		ControleTipoDocumentoAcademico controleTipoDocumentoAcademico = new ControleTipoDocumentoAcademico();
		
		controleTipoDocumentoAcademico.setModeloTipoDocumentoAcademico(modeloTipoDocumentoAcademico);
		controleTipoDocumentoAcademico.setAcompanhamentoSolicitacoes(this);
		
		controleTipoDocumentoAcademico.listarTipoDocumentoAcademicoComboAcompanhamento();
	}
	
	public void anexarArquivos(){
		documentoRequerimento.setArquivo(arquivo);
		listaDocumentoRequerimento.add(documentoRequerimento);
		documentoRequerimento = new DocumentoRequerimento();
		arquivo = new Arquivo();
	}
	
	public void excluirAnexo(DocumentoRequerimento requerimento){
		List<DocumentoRequerimento> listaAux = new ArrayList<>();
		listaAux.addAll(listaDocumentoRequerimento);
		for(DocumentoRequerimento item : listaAux){
			if(requerimento.getTipoDocumentoAcademico().equals(item.getTipoDocumentoAcademico())
					&& requerimento.getArquivo().equals(item.getArquivo())){
				listaDocumentoRequerimento.remove(requerimento);
			}
		}
	}
	
	public void salvarAcompanhamento(){
		processoAtual = etapasProcesso.getProcessoRequerimentoAcademico();
		processoAtual.setData(new Date());
		
		defineControleRequerimento().salvarAcompanhamento();
		
		salvarHistoricoEtapasProcesso();
		
		modalAcompanhamento = false;
	}
	
	private ControleRequerimentoAcademico defineControleRequerimento() {
		ModeloProcessoRequerimentoAcademico modeloProcessoRequerimentoAcademico = new ModeloProcessoRequerimentoAcademico();
		ControleRequerimentoAcademico controleRequerimentoAcademico = new ControleRequerimentoAcademico();
		
		controleRequerimentoAcademico.setModeloProcessoRequerimentoAcademico(modeloProcessoRequerimentoAcademico);
		controleRequerimentoAcademico.setAcompanhamentoSolicitacoes(this);
		
		return controleRequerimentoAcademico;
	}
	
	public void salvarHistoricoEtapasProcesso(){
		etapasProcesso.setStatus(status);
		etapasProcesso.setParecer(parecer);
		etapasProcesso.setDataFim(new Date());
		
		EtapasProcesso etapasProcessoSalva = processoDAO.saveEtapasProcesso(etapasProcesso);
		
		Historico historico = new Historico();
		historico.setEtapasProcesso(etapasProcessoSalva);
		historico.setDataHoraInicio(new Timestamp(System.currentTimeMillis()));
		historico.setParecer(etapasProcessoSalva.getParecer());
		historico.setDescricao("Etapa " + etapasProcessoSalva.getEtapa().getStringNumeroEtapa() + " / Status " + etapasProcessoSalva.getStatus().getNome());
		historico.setObservacao(observacaoAtual);
		
		historicoDAO.saveHistorico(historico);
		
	}
	
	//GETTERS E SETTERS
	public EtapasProcesso getEtapasProcesso() {
		return etapasProcesso;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public ParecerEnum getParecer() {
		return parecer;
	}
	@Override
	public ProcessoRequerimentoAcademico getProcessoAtual() {
		return processoAtual;
	}
	public List<EtapasProcesso> getListaEtapasProcesso() {
		return listaEtapasProcesso;
	}
	public List<Historico> getListaHistorico() {
		return listaHistorico;
	}
	public String getObservacaoAtual() {
		return observacaoAtual;
	}
	
	public void setEtapasProcesso(EtapasProcesso etapasProcesso) {
		this.etapasProcesso = etapasProcesso;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public void setParecer(ParecerEnum parecer) {
		this.parecer = parecer;
	}
	@Override
	public void setProcessoAtual(ProcessoRequerimentoAcademico processoAtual) {
		this.processoAtual = processoAtual;
	}
	@Override
	public void setListaEtapasProcesso(List<EtapasProcesso> listaEtapasProcesso) {
		this.listaEtapasProcesso = listaEtapasProcesso;
	}
	public void setListaHistorico(List<Historico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}
	public void setObservacaoAtual(String observacaoAtual) {
		this.observacaoAtual = observacaoAtual;
	}
	
	public StatusEnum[] getStatusArray(){
		return StatusEnum.values();
	}
	
	public ParecerEnum[] getParecerArray(){
		return ParecerEnum.values();
	}

	//Arquivos
	public Arquivo getArquivo() {
		return arquivo;
	}

	public List<Arquivo> getListaArquivos() {
		return listaArquivos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public List<UploadedFile> getFiles() {
		return files;
	}
	
	public DocumentoRequerimento getDocumentoRequerimento() {
		return documentoRequerimento;
	}
	@Override
	public List<DocumentoRequerimento> getListaDocumentoRequerimento() {
		return listaDocumentoRequerimento;
	}
	public List<DocumentoRequerimento> getListaDocumentoRequerimentoAnterior() {
		return listaDocumentoRequerimentoAnterior;
	}
	
	public List<TipoDocumentoAcademico> getListaTipoDocumentoAcademico() {
		return listaTipoDocumentoAcademico;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public void setListaArquivos(List<Arquivo> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}
	
	public void setDocumentoRequerimento(DocumentoRequerimento documentoRequerimento) {
		this.documentoRequerimento = documentoRequerimento;
	}
	
	public void setListaDocumentoRequerimento(List<DocumentoRequerimento> listaDocumentoRequerimento) {
		this.listaDocumentoRequerimento = listaDocumentoRequerimento;
	}
	@Override
	public void setListaDocumentoRequerimentoAnterior(List<DocumentoRequerimento> listaDocumentoRequerimentoAnterior) {
		this.listaDocumentoRequerimentoAnterior = listaDocumentoRequerimentoAnterior;
	}
	
	@Override
	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico) {
		this.listaTipoDocumentoAcademico = listaTipoDocumentoAcademico;
	}
	
	/**
	 * Acionado ao clicar no botão de upload
	 * @param event
	 */
	public void uploadList(FileUploadEvent event){
		
		if(event.getFile() != null){
				try {
					TrasferFile(event.getFile().getFileName(), event.getFile().getInputstream(), event.getFile());
				} catch (IOException ex) {
					Logger.getLogger(AcompanhamentoSolicitacoes.class.getName(), AcompanhamentoSolicitacoes.class).log(Level.SEVERE, null, ex);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("Erro", "Erro ao fazer o upload do arquivo"));
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sucesso", event.getFile().getFileName() + "foi carregado"));
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro","Selecione um arquivo!"));
		}
	}
	
	/**
	 * Grava o arquivo do diretório escolhido no web.xml
	 * @param fileName
	 * @param in
	 * @param file
	 */
	public void TrasferFile(String fileName, InputStream in, UploadedFile file){
		try{
			OutputStream out = new FileOutputStream(new File(destino + fileName));
			int reader = 0;
			byte[] bytes = new byte[(int)file.getSize()];
			
			while((reader = in.read(bytes)) != -1){
				out.write(bytes, 0, reader);
			}
			in.close();
			out.flush();
			out.close();
			
			arquivo.setCaminho(destino);
			arquivo.setNome(fileName);

		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}
