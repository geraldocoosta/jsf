package br.com.ultcode.livraria.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Livro {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private String titulo;
	private String isbn;
	private Double preco;
	private String dataLancamento;

	@ManyToMany
	List<Autor> autor = new ArrayList<>();
	
	public List<Autor> getAutores() {
		return autor;
	}
	
	public void adicionaAutor(Autor autor) {
		this.autor.add(autor);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

}
