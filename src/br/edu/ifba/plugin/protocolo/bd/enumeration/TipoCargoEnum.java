package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum TipoCargoEnum {

	PERMANENTE 		(1, "Cargo Permanente"),
	COMISSIONADO 	(2, "Cargo Comissionado");
	
	protected Integer id;
	protected String nome;
	
	private TipoCargoEnum() {
	}
	
	private TipoCargoEnum(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	//GETTERS
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	//SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toString(){
		return this.getNome();
	}
	
}
