package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ultcode.livraria.dao.AutorDao;
import br.com.ultcode.livraria.dao.LivroDao;
import br.com.ultcode.livraria.modelo.Autor;
import br.com.ultcode.livraria.modelo.Livro;
import br.com.ultcode.livraria.modelo.LivroDataModel;
import br.com.ultcode.livraria.util.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Livro livro = new Livro();
    private Integer autorId;
    private Integer livroId;
    private List<Livro> livros;

    @Inject
    private LivroDataModel model;

    @Inject
    private LivroDao livroDao;

    @Inject
    private AutorDao autorDao;

    public LivroDataModel getModel() {
	return model;
    }

    public void setModel(LivroDataModel model) {
	this.model = model;
    }

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

    @Transacional
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
	    livroDao.persist(livro);

	} else {
	    livroDao.atualiza(livro);
	}
	buscarLivros();
	this.livro = new Livro();

    }

    @Transacional
    public void removeAutorDoLivro(Autor autor) {
	this.livro.removeAutor(autor);
    }

    public void alterarLivro(Livro livro) {
	this.livro = livro;
	buscarLivros();
    }

    public void removerLivro(Livro livro) {
	livroDao.remove(livro);
	buscarLivros();
    }

    public void gravarAutor() {
	Autor autor = autorDao.busca(autorId);
	if (!livro.temAutor(autor)) {
	    livro.adicionaAutor(autor);
	}
    }

    public List<Autor> getAutores() {
	return autorDao.buscaTodos();
    }

    public List<Autor> getAutoresDoLivro() {
	return livro.getAutores();
    }

    public List<Livro> getLivros() {
	if (livros == null) {
	    buscarLivros();
	}
	return livros;
    }

    private void buscarLivros() {
	livros = livroDao.buscaTodos();
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
	this.livro = livroDao.busca(livroId);
	if (livro == null) {
	    this.livro = new Livro();
	}
    }

    public boolean precoMenor(Object valorColuna, Object filtroDigitado, Locale locale) {
	String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

	if (textoDigitado == null || textoDigitado.equals("")) {
	    return true;
	}

	if (valorColuna == null) {
	    return false;
	}

	try {
	    Double precoDigitado = Double.valueOf(textoDigitado);
	    Double precoColuna = (Double) valorColuna;

	    return precoColuna.compareTo(precoDigitado) < 0;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

}
