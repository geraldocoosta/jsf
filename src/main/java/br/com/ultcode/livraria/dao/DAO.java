package br.com.ultcode.livraria.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
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

    public List<T> listaTodosPaginada(int firstResult, int maxResults, Map<String, String> colunaValor) {
	EntityManager em = JPAUtil.getEntityManager();
	em.getTransaction().begin();
	CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
	CriteriaQuery<T> query = criteriaBuilder.createQuery(classe);
	Root<T> root = query.from(classe);

	String titulo = colunaValor.get("titulo");

	long preco = 0;

	try {
	    preco = Long.parseLong(colunaValor.get("preco"));
	} catch (NumberFormatException e) {
	    System.out.println("Não foi um numero que foi inserido");
	}

	if (titulo != null && !titulo.isEmpty()) {
	    query = query.where(criteriaBuilder.like(root.<String>get("titulo"), titulo + "%"));
	}

	if (preco != 0) {
	    query = query.where(criteriaBuilder.le(root.<String>get("preco").as(Long.class), preco));
	}

	List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

	em.getTransaction().commit();
	em.close();
	return lista;
    }
}
