package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum SexoEnum {

	MASCULINO (0, "Masculino"), 
	FEMININO  (1, "Feminino");
	
	protected Integer id;
	protected String nome;
	
	private SexoEnum() {
	}
	
	private SexoEnum(Integer id, String nome) {
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
