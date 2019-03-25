package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ultcode.livraria.dao.AutorDao;
import br.com.ultcode.livraria.modelo.Autor;
import br.com.ultcode.livraria.util.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable {

    private static final long serialVersionUID = -5673614687622426965L;
    private Autor autor = new Autor();
    private Integer autorId;

    @Inject
    private AutorDao autorDao;

    public Integer getAutorId() {
	return autorId;
    }

    public void setAutorId(Integer autorId) {
	this.autorId = autorId;
    }

    public void carregaAutorPorId() {
	this.autor = autorDao.busca(autorId);
	if (this.autor == null) {
	    this.autor = new Autor();
	}
    }

    public Autor getAutor() {
	return autor;
    }

    @Transacional
    public String gravar() {
	System.out.println("Registrou");
	if (autor.getId() == null) {
	    autorDao.persist(autor);
	} else {
	    autorDao.atualiza(autor);
	}
	this.autor = new Autor();
	return "livro?faces-redirect=true";
    }

    public List<Autor> buscaAutores() {
	return autorDao.buscaTodos();
    }

    @Transacional
    public void removerAutor(Autor autor) {
	autorDao.remove(autor);
    }

    public void alterarAutor(Autor autor) {
	this.autor = autor;
    }
}
