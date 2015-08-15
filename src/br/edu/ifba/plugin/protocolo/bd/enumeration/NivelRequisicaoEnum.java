package br.edu.ifba.plugin.protocolo.bd.enumeration;

public enum NivelRequisicaoEnum {

	NIVEL_REQUISICAO1 (0, "Nível Requisição 1"),
	NIVEL_REQUISICAO2 (1, "Nível Requisição 2");
	
	protected Integer id;
	protected String nome;
	
	private NivelRequisicaoEnum() {
	}
	
	private NivelRequisicaoEnum(Integer id, String nome) {
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
