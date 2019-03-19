package br.com.ultcode.livraria.beans;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ultcode.livraria.dao.UsuarioDAO;
import br.com.ultcode.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private static final String PAGES_LOGIN_REDIRECT = "login?faces-redirect=true";
	Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String logando() {

		Usuario usuario = new UsuarioDAO().confereInformacoes(this.usuario);
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
