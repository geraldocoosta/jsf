package br.com.ultcode.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.ultcode.livraria.modelo.Usuario;

public class AutorizacaoPhilter implements PhaseListener {

	private static final long serialVersionUID = -1200812529396911229L;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext context = event.getFacesContext();
		String viewId = context.getViewRoot().getViewId();

		System.out.println(viewId);

		if ("/login.xhtml".equals(viewId)) {
			return;
		}

		Usuario usuario = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		System.out.println(usuario);

		if (usuario != null) {
			return;
		}

		NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();
		navigationHandler.handleNavigation(context, null, "login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
