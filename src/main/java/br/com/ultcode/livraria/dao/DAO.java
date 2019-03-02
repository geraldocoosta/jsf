package br.com.ultcode.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
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
		em.getTransaction().commit();
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
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> query = builder.createQuery(classe);
		CriteriaQuery<T> select = query.select(query.distinct(true).from(classe));
		
		em.getTransaction().begin();
		List<T> resultList = em.createQuery(select).getResultList();
		em.getTransaction().commit();
		
		em.close();
		return resultList;
	}

	public int contaTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		em.getTransaction().commit();
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		em.getTransaction().commit();
		em.close();
		return lista;
	}
}
