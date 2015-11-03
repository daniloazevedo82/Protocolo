package br.edu.ifba.plugin.protocolo.bd.DAO;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.edu.ifba.plugin.protocolo.bd.beans.Usuario;

public class UsuarioDAO extends DAO {
	
	public Usuario getValidaUsuario(String login, String senha){
		Usuario user = new Usuario();
		
		TypedQuery<Usuario> query = em.createQuery("SELECT u "
												+ "FROM Usuario u "
												+ "LEFT JOIN FETCH u.setor s "
												+ "WHERE u.login = :login "
												+ "AND u.senha = :senha", Usuario.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		
		try {
			user = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
//			em.close();
		}
		return user;
	}

}
