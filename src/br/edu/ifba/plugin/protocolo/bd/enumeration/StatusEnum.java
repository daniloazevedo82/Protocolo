package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum StatusEnum {

	NAO_INICIADO 			(0, "Não iniciado"),
	EM_ESPERA 				(1, "Em espera"),
	EM_ANDAMENTO 			(2, "Em andamento"),
	AGUARDANDO_RESPOSTA 	(3, "Aguardando resposta"),
	CONCLUIDO				(4, "Concluído");
	
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
