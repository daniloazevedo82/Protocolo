package br.edu.ifba.plugin.protocolo.bd.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "documentorequerimento", schema = "administrativo")
@SequenceGenerator(name="sq_documentorequerimento", sequenceName="sq_documentorequerimento", schema="administrativo", allocationSize=1)
public class DocumentoRequerimento {

	private Integer id;
	private Date data;
	private ProcessoRequerimentoAcademico processoRequerimentoAcademico;
	private TipoDocumentoAcademico tipoDocumentoAcademico;
	private Arquivo arquivo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_documentorequerimento")
	public Integer getId() {
		return id;
	}
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idprocessorequerimentoacademico")
	public ProcessoRequerimentoAcademico getProcessoRequerimentoAcademico() {
		return processoRequerimentoAcademico;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipodocumentoacademico")
	public TipoDocumentoAcademico getTipoDocumentoAcademico() {
		return tipoDocumentoAcademico;
	}
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="idarquivo")
	public Arquivo getArquivo() {
		return arquivo;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public void setProcessoRequerimentoAcademico(ProcessoRequerimentoAcademico processoRequerimentoAcademico) {
		this.processoRequerimentoAcademico = processoRequerimentoAcademico;
	}
	public void setTipoDocumentoAcademico(TipoDocumentoAcademico tipoDocumentoAcademico) {
		this.tipoDocumentoAcademico = tipoDocumentoAcademico;
	}
	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((processoRequerimentoAcademico == null) ? 0 : processoRequerimentoAcademico.hashCode());
		result = prime * result + ((tipoDocumentoAcademico == null) ? 0 : tipoDocumentoAcademico.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoRequerimento other = (DocumentoRequerimento) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (processoRequerimentoAcademico == null) {
			if (other.processoRequerimentoAcademico != null)
				return false;
		} else if (!processoRequerimentoAcademico.equals(other.processoRequerimentoAcademico))
			return false;
		if (tipoDocumentoAcademico == null) {
			if (other.tipoDocumentoAcademico != null)
				return false;
		} else if (!tipoDocumentoAcademico.equals(other.tipoDocumentoAcademico))
			return false;
		return true;
	}
	
}
