package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum StatusEnum {

	EM_ESPERA 			(0, "Em espera"),
	EM_ANDAMENTO 		(1, "Em andamento"),
	CONCLUIDO 			(2, "Concluído"),
	EM_AGUARDO 			(3, "Em aguardo"),
	AGUARDANDO_RESPOSTA	(4, "Aguardando resposta");
	
	protected Integer id;
	protected String nome;
	
	private StatusEnum() {
	}
	
	private StatusEnum(Integer id, String nome) {
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
