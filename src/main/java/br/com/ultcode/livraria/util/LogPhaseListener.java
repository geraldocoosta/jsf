package br.com.ultcode.livraria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LogPhaseListener implements PhaseListener {

    /*
     * Essa classe deve ser registrada no faces-config.xml, com as tags lifecycle e
     * com um phase-listener dentro desse
     */
    private static final long serialVersionUID = 3104452964319260899L;

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public void beforePhase(PhaseEvent event) {
	System.out.println("Fase: " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
	/* com esse retorno, definimos que vamos ouvir todas as fases */
	return PhaseId.ANY_PHASE;
    }

}
