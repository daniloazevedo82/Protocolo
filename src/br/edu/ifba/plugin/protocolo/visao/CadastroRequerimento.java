package br.edu.ifba.plugin.protocolo.visao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import br.edu.ifba.plugin.protocolo.bd.beans.Aluno;
import br.edu.ifba.plugin.protocolo.bd.beans.Arquivo;
import br.edu.ifba.plugin.protocolo.bd.beans.DocumentoRequerimento;
import br.edu.ifba.plugin.protocolo.bd.beans.Etapa;
import br.edu.ifba.plugin.protocolo.bd.beans.EtapasProcesso;
import br.edu.ifba.plugin.protocolo.bd.beans.ProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.bd.beans.TipoProcesso;
import br.edu.ifba.plugin.protocolo.bd.enumeration.StatusEnum;
import br.edu.ifba.plugin.protocolo.controle.ControleAluno;
import br.edu.ifba.plugin.protocolo.controle.ControleEtapa;
import br.edu.ifba.plugin.protocolo.controle.ControleRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.controle.ControleTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloAluno;
import br.edu.ifba.plugin.protocolo.modelo.ModeloEtapa;
import br.edu.ifba.plugin.protocolo.modelo.ModeloProcessoRequerimentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoDocumentoAcademico;
import br.edu.ifba.plugin.protocolo.modelo.ModeloTipoProcesso;

@ManagedBean(name="cadastroRequerimento")
@ViewScoped
public class CadastroRequerimento implements ICadastroRequerimento{

	private final String destino = "C:\\imgProtocolo\\";
	private Arquivo arquivo = new Arquivo();
	private List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
	private UploadedFile file;
	private List<UploadedFile> files;
	private ProcessoRequerimentoAcademico processoRequerimentoAcademico = new ProcessoRequerimentoAcademico();
	private Aluno aluno;
	private List<TipoProcesso> listaTipoProcesso;
	private DocumentoRequerimento documentoRequerimento = new DocumentoRequerimento();
	private List<DocumentoRequerimento> listaDocumentoRequerimento = new ArrayList<DocumentoRequerimento>();
	private List<TipoDocumentoAcademico> listaTipoDocumentoAcademico;
	private Etapa etapa;
	private List<Etapa> listaEtapas;
	private ProcessoRequerimentoAcademico processoSalvo = new ProcessoRequerimentoAcademico();
	
	boolean modalAnexos = false;
	boolean modalConfirm = false;
	
	public boolean exibirModalAnexos(){
		return modalAnexos;
	}
	
	public void fecharModalAnexos(){
		modalAnexos = false;
	}
	
	public boolean exibirModalConfirm(){
		return modalConfirm;
	}
	
	public void fecharModalConfirm(){
		modalConfirm = false;
	}
	
	public void prepararModalAnexos(){
		documentoRequerimento = new DocumentoRequerimento();
		arquivo = new Arquivo();
		carregarListaDocumento();
		modalAnexos = true;
	}
	
	public void prepararModalConfirm(){
		modalConfirm = true;
	}
	
	public void salvarAnexos(){
		documentoRequerimento.setArquivo(arquivo);
		listaDocumentoRequerimento.add(documentoRequerimento);
		modalAnexos = false;
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

	@Override
	public ProcessoRequerimentoAcademico getProcessoRequerimentoAcademico() {
		return processoRequerimentoAcademico;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public List<TipoProcesso> getListaTipoProcesso() {
		return listaTipoProcesso;
	}
	public DocumentoRequerimento getDocumentoRequerimento() {
		return documentoRequerimento;
	}
	@Override
	public List<DocumentoRequerimento> getListaDocumentoRequerimento() {
		return listaDocumentoRequerimento;
	}
	public List<TipoDocumentoAcademico> getListaTipoDocumentoAcademico() {
		return listaTipoDocumentoAcademico;
	}
	public Etapa getEtapa() {
		return etapa;
	}
	public List<Etapa> getListaEtapas() {
		return listaEtapas;
	}
	@Override
	public ProcessoRequerimentoAcademico getProcessoSalvo() {
		return processoSalvo;
	}

	public void setProcessoRequerimentoAcademico(ProcessoRequerimentoAcademico processoRequerimentoAcademico) {
		this.processoRequerimentoAcademico = processoRequerimentoAcademico;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	@Override
	public void setListaTipoProcesso(List<TipoProcesso> listaTipoProcesso) {
		this.listaTipoProcesso = listaTipoProcesso;
	}
	public void setDocumentoRequerimento(DocumentoRequerimento documentoRequerimento) {
		this.documentoRequerimento = documentoRequerimento;
	}
	public void setListaDocumentoRequerimento(List<DocumentoRequerimento> listaDocumentoRequerimento) {
		this.listaDocumentoRequerimento = listaDocumentoRequerimento;
	}
	@Override
	public void setListaTipoDocumentoAcademico(List<TipoDocumentoAcademico> listaTipoDocumentoAcademico) {
		this.listaTipoDocumentoAcademico = listaTipoDocumentoAcademico;
	}
	@Override
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	@Override
	public void setListaEtapas(List<Etapa> listaEtapas) {
		this.listaEtapas = listaEtapas;
	}
	@Override
	public void setProcessoSalvo(ProcessoRequerimentoAcademico processoSalvo) {
		this.processoSalvo = processoSalvo;
	}
	
	public Date getDataAtual(){
		return new Date();
	}
	
	public List<Aluno> getTextoAlunoAutocomplete(String nome){
		ModeloAluno modeloAluno = new ModeloAluno();
		ControleAluno controleAluno = new ControleAluno();
		
		controleAluno.setModeloAluno(modeloAluno);
		
		List<Aluno> listaAluno = controleAluno.carregaListaAlunoAutocomplete(nome); 
		
		return listaAluno;
	}
	
	public void clearAutocomplete(){
		processoRequerimentoAcademico.setAluno(new Aluno());
	}
	
	public void carregarListaTipoProcesso(){
		ModeloTipoProcesso modeloTipoProcesso = new ModeloTipoProcesso();
		ControleRequerimentoAcademico controleRequerimentoAcademico = new ControleRequerimentoAcademico();
		
		controleRequerimentoAcademico.setModeloTipoProcesso(modeloTipoProcesso);
		controleRequerimentoAcademico.setCadastroRequerimento(this);
		
		controleRequerimentoAcademico.carregaListaTipoProcessoComboRequerimento();
	}
	
	public void carregarListaDocumento(){
		ModeloTipoDocumentoAcademico modeloTipoDocumentoAcademico = new ModeloTipoDocumentoAcademico();
		ControleTipoDocumentoAcademico controleTipoDocumentoAcademico = new ControleTipoDocumentoAcademico();
		
		controleTipoDocumentoAcademico.setModeloTipoDocumentoAcademico(modeloTipoDocumentoAcademico);
		controleTipoDocumentoAcademico.setCadastroRequerimento(this);
		
		controleTipoDocumentoAcademico.listarTipoDocumentoAcademicoCombo();
	}
	
	public void carregarEtapa(){
		ModeloEtapa modeloEtapa = new ModeloEtapa();
		ControleEtapa controleEtapa = new ControleEtapa();
		
		controleEtapa.setModeloEtapa(modeloEtapa);
		controleEtapa.setCadastroRequerimento(this);
		
		controleEtapa.carregaEtapaCadastroRequerimento();
	}
	
	public void salvarRequerimento(){
		processoRequerimentoAcademico.setData(getDataAtual());
		
		defineControleRequerimento().salvarRequerimento();
		
		criarEtapasProcesso();
		
		modalConfirm = true;
	}
	
	public void redirecionarTelaInicial(){  
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.ifba");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private ControleRequerimentoAcademico defineControleRequerimento() {
		ModeloProcessoRequerimentoAcademico modeloProcessoRequerimentoAcademico = new ModeloProcessoRequerimentoAcademico();
		ControleRequerimentoAcademico controleRequerimentoAcademico = new ControleRequerimentoAcademico();
		
		controleRequerimentoAcademico.setModeloProcessoRequerimentoAcademico(modeloProcessoRequerimentoAcademico);
		controleRequerimentoAcademico.setCadastroRequerimento(this);
		
		return controleRequerimentoAcademico;
	}
	
	public void criarEtapasProcesso(){
		List<EtapasProcesso> listaEtapasProcesso = new ArrayList<EtapasProcesso>();
		carregarEtapas();
		if(listaEtapas != null && !listaEtapas.isEmpty()){
			for(Etapa item : listaEtapas){
				EtapasProcesso etapasProcesso = new EtapasProcesso();
				etapasProcesso.setEtapa(item);
				etapasProcesso.setProcessoRequerimentoAcademico(processoSalvo);
				if(item.getNrSequencia().equals(1)){
					etapasProcesso.setDataInicio(processoSalvo.getData());
					etapasProcesso.setDataFim(processoSalvo.getData());
					etapasProcesso.setStatus(StatusEnum.CONCLUIDO);
				} else if(item.getNrSequencia().equals(2)){
					etapasProcesso.setStatus(StatusEnum.EM_ESPERA);
				} else {
					etapasProcesso.setStatus(StatusEnum.NAO_INICIADO);
				}
				listaEtapasProcesso.add(etapasProcesso);
			}
		}
		salvarListaEtapas(listaEtapasProcesso);
	}
	
	public void carregarEtapas(){
		ModeloEtapa modeloEtapa = new ModeloEtapa();
		ControleEtapa controleEtapa = new ControleEtapa();
		
		controleEtapa.setModeloEtapa(modeloEtapa);
		controleEtapa.setCadastroRequerimento(this);
		
		controleEtapa.carregaListaEtapaCadastroRequerimento();
	}
	
	public void salvarListaEtapas(List<EtapasProcesso> listaEtapasProcesso){
		defineControleRequerimento().salvarEtapasProcesso(listaEtapasProcesso);
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
					Logger.getLogger(CadastroRequerimento.class.getName(), CadastroRequerimento.class).log(Level.SEVERE, null, ex);
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
	
}