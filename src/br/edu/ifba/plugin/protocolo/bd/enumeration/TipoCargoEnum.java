package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum TipoCargoEnum {

	TIPO_CARGO1 (0, "Tipo Cargo 1"),
	TIPO_CARGO2 (1, "Tipo Cargo 2");
	
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
