package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum ParecerEnum {
	
	DEFERIDO					(0, "Deferido"),
	INDEFERIDO   				(1, "Indeferido");
	
	private ParecerEnum() {
	}
	
	private ParecerEnum(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	protected Integer id;
	protected String nome;
	
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
