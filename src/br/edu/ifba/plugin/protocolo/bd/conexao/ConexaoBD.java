package br.edu.ifba.plugin.protocolo.bd.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoBD {

	private EntityManagerFactory managerFactory = null;

	// INSTÂNCIA SINGLETON PARA ACESSO A CONEXÕES
	private static ConexaoBD instancia = null;
	
	public static ConexaoBD getInstancia() {
		if (instancia == null) {
			instancia = new ConexaoBD();
			instancia.iniciar();
		}
		
		return instancia;
	}
	
	private ConexaoBD() {
	}

	public void iniciar() {
		managerFactory = Persistence.createEntityManagerFactory("plugin");
	}

	public void fechar() {
		managerFactory.close();
	}

	public EntityManager getEntityManager() {
		return managerFactory.createEntityManager();
	}

}