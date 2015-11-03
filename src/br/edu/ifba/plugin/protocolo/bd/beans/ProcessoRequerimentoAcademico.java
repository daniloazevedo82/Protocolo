package br.edu.ifba.plugin.protocolo.bd.beans;

import java.beans.Transient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="processorequerimentoacademico", schema="administrativo")
public class ProcessoRequerimentoAcademico extends Processo{

	private Aluno aluno;
	private String observacoes;
	
	private List<DocumentoRequerimento> listaDocumentoRequerimento = new ArrayList<DocumentoRequerimento>();
	private List<Historico> listaHistorico = new ArrayList<Historico>();
	
	public ProcessoRequerimentoAcademico(){
	}

	public ProcessoRequerimentoAcademico(Aluno aluno, String observacoes) {
		this.aluno = aluno;
		this.observacoes = observacoes;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idaluno")
	public Aluno getAluno() {
		return aluno;
	}

	public String getObservacoes() {
		return observacoes;
	}
	
	@OneToMany(mappedBy="processoRequerimentoAcademico", cascade = CascadeType.PERSIST)
	public List<DocumentoRequerimento> getListaDocumentoRequerimento() {
		return listaDocumentoRequerimento;
	}
	
	@Transient
	public List<Historico> getListaHistorico() {
		return listaHistorico;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public void setListaDocumentoRequerimento(List<DocumentoRequerimento> listaDocumentoRequerimento) {
		this.listaDocumentoRequerimento = listaDocumentoRequerimento;
	}
	
	public void setListaHistorico(List<Historico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}
	
	@Transient
	public String getAnexosString(){
		String anexosString = "";
		if(listaDocumentoRequerimento != null && !listaDocumentoRequerimento.isEmpty()){
			for(DocumentoRequerimento item : listaDocumentoRequerimento){
				anexosString += item.getTipoDocumentoAcademico().getNome() + ": " +
								item.getArquivo().getNome() + "\n";
			}
		}
		return anexosString;
	}
	
	@Transient
	public String getNumeroProtocoloString(){
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		String protocolo = "";
		if(id != null){
			protocolo += id.toString() + "/" + ano.format(data);
		}
		return protocolo;
	}

	@Override
	public String toString() {
		return "ProcessoRequerimentoAcademico [aluno=" + aluno + ", observacoes=" + observacoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((observacoes == null) ? 0 : observacoes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessoRequerimentoAcademico other = (ProcessoRequerimentoAcademico) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (observacoes == null) {
			if (other.observacoes != null)
				return false;
		} else if (!observacoes.equals(other.observacoes))
			return false;
		return true;
	}
	
}
