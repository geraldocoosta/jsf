package br.com.ultcode.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> {

	private final Class<T> classe;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public void persist(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		em.close();
	}

	public void atualiza(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		em.close();
	}

	public T busca(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		T instancia = em.find(classe, id);
		em.close();
		return instancia;
	}

	public void remove(T t) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(t));
		em.getTransaction().commit();
		em.close();
	}

	public List<T> buscaTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaQuery<T> criteria = em.getCriteriaBuilder().createQuery(classe);
		criteria.select(criteria.from(classe));
		List<T> lista = em.createQuery(criteria).getResultList();
		em.close();
		return lista;
	}

	public int contaTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		em.close();
		return lista;
	}
}
