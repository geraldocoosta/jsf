package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;

	EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.classe = classe;
		em = manager;
	}

	public void persist(T t) {
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}

	public void atualiza(T t) {
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
	}

	public T busca(Integer id) {
		em.getTransaction().begin();
		T instancia = em.find(classe, id);
		em.getTransaction().commit();
		return instancia;
	}

	public void remove(T t) {
		em.getTransaction().begin();
		em.remove(em.merge(t));
		em.getTransaction().commit();
	}

	public List<T> buscaTodos() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> query = builder.createQuery(classe);
		CriteriaQuery<T> select = query.select(query.distinct(true).from(classe));

		em.getTransaction().begin();
		List<T> resultList = em.createQuery(select).getResultList();
		em.getTransaction().commit();

		return resultList;
	}

	public int contaTodos() {
		em.getTransaction().begin();
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		em.getTransaction().commit();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		em.getTransaction().begin();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		em.getTransaction().commit();
		return lista;
	}


}
