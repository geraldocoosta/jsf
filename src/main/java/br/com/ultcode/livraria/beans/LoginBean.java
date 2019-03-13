package br.com.ultcode.livraria.beans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ultcode.livraria.dao.UsuarioDAO;
import br.com.ultcode.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public String logando() {

		Usuario usuario = new UsuarioDAO().confereInformacoes(this.usuario);

		if (usuario != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
			sessionMap.put("usuarioLogado", usuario);
			return "livro.xhtml?faces-redirect=true";
		}

		return null;
	}
}
