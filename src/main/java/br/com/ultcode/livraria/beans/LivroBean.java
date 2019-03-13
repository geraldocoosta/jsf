package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.ultcode.livraria.dao.DAO;
import br.com.ultcode.livraria.modelo.Autor;
import br.com.ultcode.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Livro livro = new Livro();
    private Integer autorId;
    private Integer livroId;

    public Integer getLivroId() {
	return livroId;
    }

    public void setLivroId(Integer livroId) {
	this.livroId = livroId;
    }

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
//			throw new RuntimeException("Livro deve ter pelo menos um Autor.");
	    /*
	     * Aqui, em vez de lançarmos uma exceção, vamos lançar um error message, para
	     * aparecer bonitinho no front
	     */
	    FacesContext.getCurrentInstance().addMessage("autor",
		    new FacesMessage("Livro deve ter pelo menos um autor"));
	    return;
	}

	if (livro.getId() == null) {
	    new DAO<Livro>(Livro.class).persist(livro);
	} else {
	    new DAO<Livro>(Livro.class).atualiza(livro);
	}
	this.livro = new Livro();

    }

    public void removeAutorDoLivro(Autor autor) {
	this.livro.removeAutor(autor);
    }

    public void alterarLivro(Livro livro) {
	this.livro = livro;
    }

    public void removerLivro(Livro livro) {
	new DAO<Livro>(Livro.class).remove(livro);
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

    /*
     * Ele recebe um FacesContext, um objeto que permite obter informações da view
     * processada no momento. O segundo parâmetro é o UIComponent, o componente da
     * view que está sendo validado. Por último, temos um objeto que é o valor
     * digitado pelo usuário. Este método precisa lançar um ValidatorException,
     * exceção que sinalizará para o JSF que algo saiu errado
     */
    public void comecaComDigitoUm(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	String valor = value.toString();

	if (valor.startsWith("1")) {
	    /*
	     * Ao vermos algo que não deve passar pela validação, devemos soltar um exceção
	     * Validator Excelption, que recebe um objeto FacesMessage com a mensagem, o
	     * resto da configuração fica no JSF
	     * 
	     * Dentro do xml do jsf deve-se usar uma tag validator apontando para esse
	     * método, em um input
	     */
	    throw new ValidatorException(new FacesMessage("Não deveria começar com 1"));
	}
    }

    public String formAutor() {
	System.out.println("Chamando o formulario autor");
	/*
	 * aqui, devemos deixar explicito que queremos que o navegador faça o redirect,
	 * no caso está fazendo um forward
	 */
	return "autor?faces-redirect=true";
    }

    public void carregaAutorPorId() {
	this.livro = new DAO<Livro>(Livro.class).busca(livroId);
	if (livro == null) {
	    this.livro = new Livro();
	}
    }

}
