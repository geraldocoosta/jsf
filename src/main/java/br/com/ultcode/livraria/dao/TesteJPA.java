package br.com.ultcode.livraria.dao;

import javax.persistence.EntityManager;

import br.com.ultcode.livraria.modelo.Autor;

public class TesteJPA {
	public static void main(String[] args) {
		
		Autor autor = new Autor();
		autor.setNome("gege");
		
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(autor);
		em.getTransaction().commit();
	}
}
