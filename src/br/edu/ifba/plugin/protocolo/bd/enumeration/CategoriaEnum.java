package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum CategoriaEnum {

	ALUNO 					(1, "Aluno"),
	TECNICO_ADMINISTRATIVO 	(2, "Técnico Administrativo"),
	PROFESSOR_EBTT 			(3, "Professor EBTT"),
	PROFESSOR_NS 			(4, "Professor Nível Superior");
	
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
