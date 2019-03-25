package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ultcode.livraria.modelo.Autor;

public class AutorDao implements Serializable {

    private static final long serialVersionUID = 7237372867940712873L;
    @Inject
    private EntityManager manager;
    private DAO<Autor> dao;

    @PostConstruct
    private void init() {
	dao = new DAO<>(manager, Autor.class);
    }

    public void persist(Autor t) {
	dao.persist(t);
    }

    public void atualiza(Autor t) {
	dao.atualiza(t);
    }

    public Autor busca(Integer id) {
	return dao.busca(id);
    }

    public void remove(Autor t) {
	dao.remove(t);
    }

    public List<Autor> buscaTodos() {
	return dao.buscaTodos();
    }

}
