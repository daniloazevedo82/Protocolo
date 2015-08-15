package br.edu.ifba.plugin.protocolo.bd.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tipoprocesso", schema="administrativo")
@SequenceGenerator(name="sq_tipoprocesso", sequenceName="sq_tipoprocesso", schema="administrativo", allocationSize=1)
public class TipoProcesso {
	
	public static final TipoProcesso PROGRESSAO_FUNCIONAL = new TipoProcesso(1, "Progress�o Funcional");
	public static final TipoProcesso SOLICITACAO_COMPRAS = new TipoProcesso(2, "Solicita��o de compras");
	public static final TipoProcesso APROVEITAMENTO_CONTEUDO = new TipoProcesso(3, "Aproveitamento de Conte�do");
	public static final TipoProcesso EXERCICIO_DOMICILIAR = new TipoProcesso(4, "Exerc�cio Domiciliar");
	public static final TipoProcesso REINTEGRACAO_CURSO = new TipoProcesso(5, "Reintegra��o de curso");
	public static final TipoProcesso REVISAO_AVALIACAO_DISCIPLINA = new TipoProcesso(6, "Revis�o de avalia��o de disciplina");
	public static final TipoProcesso SEGUNDA_CHAMADA_DISCIPLINA = new TipoProcesso(7, "Segunda chamada de disciplina");
	public static final TipoProcesso TRANCAMENTO_DISCIPLINA = new TipoProcesso(8, "Trancamento de disciplina");
	public static final TipoProcesso TRANSFERENCIA_INTERNA = new TipoProcesso(9, "Transfer�ncia interna");
	public static final TipoProcesso TRANSFERENCIA_EXTERNA = new TipoProcesso(10, "Transfer�ncia externa");
	
	private Integer id;
	private String nome;
	
	public TipoProcesso(){
	}
	
	public TipoProcesso(Integer id){
		this.id = id;
	}
	
	public TipoProcesso(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	//GETTERS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sq_tipoprocesso")
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
		TipoProcesso other = (TipoProcesso) obj;
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
