package br.com.ultcode.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.ultcode.livraria.modelo.Usuario;

public class UsuarioDAO {

	public Usuario confereInformacoes(Usuario usuario) {

		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		TypedQuery<Usuario> query = em.createQuery(
				"select u from Usuario u where u.email = :pUserEmail and u.senha = :pUserSenha", Usuario.class);
		query.setParameter("pUserEmail", usuario.getEmail());
		query.setParameter("pUserSenha", usuario.getSenha());
		Usuario user;
		try {
			user = query.getSingleResult();
			em.getTransaction().commit();
			System.out.println(user);
			return user;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return null;
		}finally {
			em.close();
		}
	}

}
