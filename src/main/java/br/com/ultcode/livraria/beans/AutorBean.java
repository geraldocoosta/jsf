package br.com.ultcode.livraria.beans;

import javax.faces.bean.ManagedBean;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();
	
	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Registrou");
		new DAO<>(Autor.class).persist(autor);
		this.autor = new Autor();
	}
	
}
