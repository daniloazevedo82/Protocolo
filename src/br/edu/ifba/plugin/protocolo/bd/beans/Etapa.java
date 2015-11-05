package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "etapa", schema = "administrativo")
@SequenceGenerator(name="sq_etapa", sequenceName="sq_etapa", schema="administrativo", allocationSize=1)
public class Etapa {

	private Integer id;
	private String nome;
	private Integer nrSequencia;
	private Boolean temParecer = false;
	private Boolean permiteAnexo = false;
	private Setor setor;
	private TipoProcesso tipoProcesso;
	private Boolean ultimaEtapa = false;
	private Boolean primeiraEtapa = false;
	//protected Cargo cargoAutorizador FIXME Ver como vai ficar a relação com cargo
	
	public Etapa(){
	}
	
	public Etapa(Integer id, String nome, Integer nrSequencia, Boolean temParecer, Boolean permiteAnexo, Setor setor,
			TipoProcesso tipoProcesso) {
		this.id = id;
		this.nome = nome;
		this.nrSequencia = nrSequencia;
		this.temParecer = temParecer;
		this.permiteAnexo = permiteAnexo;
		this.setor = setor;
		this.tipoProcesso = tipoProcesso;
	}
	
	//GETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_etapa")
	public Integer getId() {
		return id;
	}
	
	public String getNome() {
		if(StringUtils.isBlank(nome)){
			nome = "";
		}
		return nome;
	}

	public Integer getNrSequencia() {
		return nrSequencia;
	}

	public Boolean getTemParecer() {
		return temParecer;
	}

	public Boolean getPermiteAnexo() {
		return permiteAnexo;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsetor")
	public Setor getSetor() {
		return setor;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipoprocesso")
	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}
	
	public Boolean getUltimaEtapa() {
		return ultimaEtapa;
	}
	
	public Boolean getPrimeiraEtapa() {
		return primeiraEtapa;
	}

	//SETTERS
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNrSequencia(Integer nrSequencia) {
		this.nrSequencia = nrSequencia;
	}

	public void setTemParecer(Boolean temParecer) {
		this.temParecer = temParecer;
	}

	public void setPermiteAnexo(Boolean permiteAnexo) {
		this.permiteAnexo = permiteAnexo;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public void setTipoProcesso(TipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}
	
	public void setUltimaEtapa(Boolean ultimaEtapa) {
		this.ultimaEtapa = ultimaEtapa;
	}
	
	public void setPrimeiraEtapa(Boolean primeiraEtapa) {
		this.primeiraEtapa = primeiraEtapa;
	}
	
	public String getTemParecerString(){
		if(temParecer != null && temParecer){
			return "Sim";
		} else {
			return "Não";
		}
	}
	
	public String getPermiteAnexoString(){
		if(permiteAnexo != null && permiteAnexo){
			return "Sim";
		} else {
			return "Não";
		}
	}

	public String getStringNumeroEtapa(){
		return nrSequencia.toString() + "- " + nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nrSequencia == null) ? 0 : nrSequencia.hashCode());
		result = prime * result + ((permiteAnexo == null) ? 0 : permiteAnexo.hashCode());
		result = prime * result + ((primeiraEtapa == null) ? 0 : primeiraEtapa.hashCode());
		result = prime * result + ((setor == null) ? 0 : setor.hashCode());
		result = prime * result + ((temParecer == null) ? 0 : temParecer.hashCode());
		result = prime * result + ((tipoProcesso == null) ? 0 : tipoProcesso.hashCode());
		result = prime * result + ((ultimaEtapa == null) ? 0 : ultimaEtapa.hashCode());
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
		Etapa other = (Etapa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nrSequencia == null) {
			if (other.nrSequencia != null)
				return false;
		} else if (!nrSequencia.equals(other.nrSequencia))
			return false;
		if (permiteAnexo == null) {
			if (other.permiteAnexo != null)
				return false;
		} else if (!permiteAnexo.equals(other.permiteAnexo))
			return false;
		if (primeiraEtapa == null) {
			if (other.primeiraEtapa != null)
				return false;
		} else if (!primeiraEtapa.equals(other.primeiraEtapa))
			return false;
		if (setor == null) {
			if (other.setor != null)
				return false;
		} else if (!setor.equals(other.setor))
			return false;
		if (temParecer == null) {
			if (other.temParecer != null)
				return false;
		} else if (!temParecer.equals(other.temParecer))
			return false;
		if (tipoProcesso == null) {
			if (other.tipoProcesso != null)
				return false;
		} else if (!tipoProcesso.equals(other.tipoProcesso))
			return false;
		if (ultimaEtapa == null) {
			if (other.ultimaEtapa != null)
				return false;
		} else if (!ultimaEtapa.equals(other.ultimaEtapa))
			return false;
		return true;
	}

}