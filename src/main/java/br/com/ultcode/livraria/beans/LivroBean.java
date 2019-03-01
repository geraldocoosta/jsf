package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Autor;
import br.com.ultcode.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Livro livro = new Livro();
    private Integer autorId;

    public Livro getLivro() {
	return livro;
    }

    public Integer getAutorId() {
	return autorId;
    }

    public void setAutorId(Integer autorId) {
	this.autorId = autorId;
    }

    public void gravar() {

	if (livro.getAutores().isEmpty()) {
	    throw new RuntimeException("Livro deve ter pelo menos um Autor.");
	}
	new DAO<Livro>(Livro.class).persist(livro);
	this.livro = new Livro();
    }

    public void gravarAutor() {
	Autor autor = new DAO<Autor>(Autor.class).busca(autorId);
	livro.adicionaAutor(autor);
    }

    public List<Autor> getAutores() {
	return new DAO<Autor>(Autor.class).buscaTodos();
    }

    public List<Autor> getAutoresDoLivro() {
	return livro.getAutores();
    }

    public List<Livro> getLivros() {
	return new DAO<Livro>(Livro.class).buscaTodos();
    }

}
