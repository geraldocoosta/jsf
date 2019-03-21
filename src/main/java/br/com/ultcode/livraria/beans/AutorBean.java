package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable {

    private static final long serialVersionUID = -5673614687622426965L;
    private Autor autor = new Autor();
    private Integer autorId;

    public Integer getAutorId() {
	return autorId;
    }

    public void setAutorId(Integer autorId) {
	this.autorId = autorId;
    }

    public void carregaAutorPorId() {
	this.autor = new DAO<>(Autor.class).busca(autorId);
	if (this.autor == null) {
	    this.autor = new Autor();
	}
    }

    public Autor getAutor() {
	return autor;
    }

    public String gravar() {
	System.out.println("Registrou");
	if (autor.getId() == null) {
	    new DAO<>(Autor.class).persist(autor);
	} else {
	    new DAO<>(Autor.class).atualiza(autor);
	}
	this.autor = new Autor();
	return "livro?faces-redirect=true";
    }

    public List<Autor> buscaAutores() {
	return new DAO<Autor>(Autor.class).buscaTodos();
    }

    public void removerAutor(Autor autor) {
	new DAO<>(Autor.class).remove(autor);
    }

    public void alterarAutor(Autor autor) {
	this.autor = autor;
    }
}
