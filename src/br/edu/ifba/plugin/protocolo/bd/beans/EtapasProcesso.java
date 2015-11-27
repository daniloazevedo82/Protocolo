package br.edu.ifba.plugin.protocolo.bd.beans;

import java.util.Date;

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
import javax.persistence.Transient;

import br.edu.ifba.plugin.protocolo.bd.enumeration.ParecerEnum;
import br.edu.ifba.plugin.protocolo.bd.enumeration.StatusEnum;

@Entity
@Table(name = "etapasprocesso", schema = "administrativo")
@SequenceGenerator(name="sq_etapasprocesso", sequenceName="sq_etapasprocesso", schema="administrativo", allocationSize=1)
public class EtapasProcesso {

	private Integer id;
	private Date dataInicio;
	private Date dataFim;
	private StatusEnum status;
	private ParecerEnum parecer;
	private Etapa etapa;
	private ProcessoRequerimentoAcademico processoRequerimentoAcademico;
	
	//TRANSIENT
	private Setor localizacao;
		
	public EtapasProcesso() {
	}
	
	public EtapasProcesso(Integer id, Date dataInicio, Date dataFim, StatusEnum status, ParecerEnum parecer,
			Etapa etapa, ProcessoRequerimentoAcademico processoRequerimentoAcademico) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.status = status;
		this.parecer = parecer;
		this.etapa = etapa;
		this.processoRequerimentoAcademico = processoRequerimentoAcademico;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_etapasprocesso")
	public Integer getId() {
		return id;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataInicio() {
		return dataInicio;
	}
	@Temporal(TemporalType.DATE)
	public Date getDataFim() {
		return dataFim;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public ParecerEnum getParecer() {
		return parecer;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idetapa")
	public Etapa getEtapa() {
		return etapa;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idprocessorequerimento")
	public ProcessoRequerimentoAcademico getProcessoRequerimentoAcademico() {
		return processoRequerimentoAcademico;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public void setParecer(ParecerEnum parecer) {
		this.parecer = parecer;
	}
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	public void setProcessoRequerimentoAcademico(ProcessoRequerimentoAcademico processoRequerimentoAcademico) {
		this.processoRequerimentoAcademico = processoRequerimentoAcademico;
	}
	
	@Transient
	public Setor getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(Setor localizacao) {
		this.localizacao = localizacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((etapa == null) ? 0 : etapa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((parecer == null) ? 0 : parecer.hashCode());
		result = prime * result
				+ ((processoRequerimentoAcademico == null) ? 0 : processoRequerimentoAcademico.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		EtapasProcesso other = (EtapasProcesso) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (etapa == null) {
			if (other.etapa != null)
				return false;
		} else if (!etapa.equals(other.etapa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parecer != other.parecer)
			return false;
		if (processoRequerimentoAcademico == null) {
			if (other.processoRequerimentoAcademico != null)
				return false;
		} else if (!processoRequerimentoAcademico.equals(other.processoRequerimentoAcademico))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
}
