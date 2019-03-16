package br.com.ultcode.livraria.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

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
