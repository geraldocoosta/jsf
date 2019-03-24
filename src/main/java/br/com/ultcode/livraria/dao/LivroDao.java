package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.ultcode.livraria.modelo.Livro;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;
	private DAO<Livro> dao;

	@PostConstruct
	private void init() {
		dao = new DAO<>(manager, Livro.class);
	}

	public void persist(Livro t) {
		dao.persist(t);
	}

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	public Livro busca(Integer id) {
		return dao.busca(id);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

	public List<Livro> buscaTodos() {
		return dao.buscaTodos();
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults, Map<String, String> colunaValor) {
		manager.getTransaction().begin();
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Livro> query = criteriaBuilder.createQuery(Livro.class);
		Root<Livro> root = query.from(Livro.class);

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
			query = query.where(criteriaBuilder.le(root.<Long>get("preco"), preco));
		}

		List<Livro> lista = manager.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		manager.getTransaction().commit();
		return lista;
	}

}
