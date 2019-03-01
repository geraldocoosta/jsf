package br.com.ultcode.livraria.beans;

import javax.faces.bean.ManagedBean;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Livro;

@ManagedBean
public class LivroBean {

	Livro livro = new Livro();

	public Livro getLivro() {
		return livro;
	}

	public void gravar() {

		if (livro.getAutores().isEmpty()) {
			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
		}	
		new DAO<Livro>(Livro.class).persist(livro);
	}
}
