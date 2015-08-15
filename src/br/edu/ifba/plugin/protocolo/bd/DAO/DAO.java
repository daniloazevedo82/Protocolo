package br.edu.ifba.plugin.protocolo.bd.DAO;

import javax.persistence.EntityManager;

import br.edu.ifba.plugin.protocolo.bd.conexao.ConexaoBD;

public abstract class DAO {

	protected EntityManager em;

	public DAO() {
		em = ConexaoBD.getInstancia().getEntityManager();
	}

	public void iniciarTransacao() {
		if (em.getTransaction().isActive() == false) {
			em.getTransaction().begin();
		}
	}

	public void rollbackTransacao() {
		em.getTransaction().rollback();
	}

	public void commitTransacao() {
		em.getTransaction().commit();
	}

}
