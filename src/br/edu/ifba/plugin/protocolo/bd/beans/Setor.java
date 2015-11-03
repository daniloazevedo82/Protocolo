package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="setor", schema="administrativo")
@SequenceGenerator(name="sq_setor", sequenceName="sq_setor", schema="administrativo", allocationSize=1)
public class Setor {

	public static final Setor PROTOCOLO = new Setor(1, "Protocolo");
	public static final Setor DEN = new Setor(2, "DEN");
	public static final Setor DG = new Setor(3, "DG");
	public static final Setor DIREH = new Setor(4, "DIREH");
	public static final Setor PLANEJAMENTO = new Setor(5, "Planejamento");
	public static final Setor DAP = new Setor(6, "DAP");
	public static final Setor SETOR_COMPRAS = new Setor(7, "Setor de compras");
	public static final Setor CORES = new Setor(8, "CORES");
	public static final Setor CSI = new Setor(9, "Coordenação de BSI");
	public static final Setor COINFO = new Setor(10, "Coordenação de Informática");
	
	private Integer id;
	private String nome;
	
	public Setor() {
	}
	
	public Setor(Integer id){
		this.id = id;
	}
	
	public Setor(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	//GETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_setor")
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	//SETTERS
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
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
		Setor other = (Setor) obj;
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
		return true;
	}
	
}
