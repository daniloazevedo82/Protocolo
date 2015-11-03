package br.edu.ifba.plugin.protocolo.bd.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.edu.ifba.plugin.protocolo.bd.enumeration.ParecerEnum;

@Entity
@Table(name = "historico", schema = "administrativo")
@SequenceGenerator(name="sq_historico", sequenceName="sq_historico", schema="administrativo", allocationSize=1)
public class Historico {

	private Integer id;
	private String descricao;
	private String observacao;
	private ParecerEnum parecer;
	private Timestamp dataHoraInicio;
	private Timestamp dataHoraFim;
	private EtapasProcesso etapasProcesso;
//	private Usuario usuario;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_historico")
	public Integer getId() {
		return id;
	}
	public String getDescricao() {
		return descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public ParecerEnum getParecer() {
		return parecer;
	}
	
	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}
	
	public Timestamp getDataHoraFim() {
		return dataHoraFim;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idetapasprocesso")
	public EtapasProcesso getEtapasProcesso() {
		return etapasProcesso;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public void setParecer(ParecerEnum parecer) {
		this.parecer = parecer;
	}
	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}
	public void setDataHoraFim(Timestamp dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
	public void setEtapasProcesso(EtapasProcesso etapasProcesso) {
		this.etapasProcesso = etapasProcesso;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHoraFim == null) ? 0 : dataHoraFim.hashCode());
		result = prime * result + ((dataHoraInicio == null) ? 0 : dataHoraInicio.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((etapasProcesso == null) ? 0 : etapasProcesso.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((parecer == null) ? 0 : parecer.hashCode());
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
		Historico other = (Historico) obj;
		if (dataHoraFim == null) {
			if (other.dataHoraFim != null)
				return false;
		} else if (!dataHoraFim.equals(other.dataHoraFim))
			return false;
		if (dataHoraInicio == null) {
			if (other.dataHoraInicio != null)
				return false;
		} else if (!dataHoraInicio.equals(other.dataHoraInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (etapasProcesso == null) {
			if (other.etapasProcesso != null)
				return false;
		} else if (!etapasProcesso.equals(other.etapasProcesso))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (parecer != other.parecer)
			return false;
		return true;
	}
	
}
