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
		em.persist(t);
	}

	public void atualiza(T t) {
		em.merge(t);
	}

	public T busca(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public List<T> buscaTodos() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> query = builder.createQuery(classe);
		CriteriaQuery<T> select = query.select(query.distinct(true).from(classe));

		List<T> resultList = em.createQuery(select).getResultList();

		return resultList;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		return lista;
	}


}
