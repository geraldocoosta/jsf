package br.com.ultcode.livraria.beans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ultcode.livraria.dao.UsuarioDao;
import br.com.ultcode.livraria.modelo.Usuario;


@Named
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 2720633990755071733L;
    private static final String PAGES_LOGIN_REDIRECT = "login?faces-redirect=true";

    private Usuario usuario;

    @Inject
    private UsuarioDao usuarioDAO;

    @PostConstruct
    public void init() {
	usuario = new Usuario();
    }

    public Usuario getUsuario() {
	return usuario;
    }

    public String logando() {

	Usuario usuario = usuarioDAO.confereInformacoes(this.usuario);
	FacesContext context = FacesContext.getCurrentInstance();

	System.out.println(usuario);
	if (usuario != null) {
	    Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
	    sessionMap.put("usuarioLogado", usuario);
	    return "livro?faces-redirect=true";
	}

	context.getExternalContext().getFlash().setKeepMessages(true);
	context.addMessage(null, new FacesMessage("Usuario não encontrado"));

	return PAGES_LOGIN_REDIRECT;
    }

    public String deslogar() {
	FacesContext context = FacesContext.getCurrentInstance();
	context.getExternalContext().getSessionMap().remove("usuarioLogado");
	usuario = new Usuario();
	return PAGES_LOGIN_REDIRECT;
    }
}
