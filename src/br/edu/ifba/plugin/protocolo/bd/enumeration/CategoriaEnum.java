package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum CategoriaEnum {

	ALUNO 					(1, "Aluno"),
	PROFESSOR 				(2, "Professor"),
	TECNICO_ADMINISTRATIVO 	(3, "Técnico Administrativo");
	
	protected Integer id;
	protected String nome;
	
	private CategoriaEnum() {
	}
	
	private CategoriaEnum(Integer id, String nome) {
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
