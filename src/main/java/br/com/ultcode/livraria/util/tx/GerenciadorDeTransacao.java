package br.com.ultcode.livraria.util.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Interceptor
@Transacional
public class GerenciadorDeTransacao implements Serializable {

    private static final long serialVersionUID = 3058392181789527067L;
    @Inject
    EntityManager manager;

    @AroundInvoke
    public Object abreFechaTransacao(InvocationContext context) throws Exception {

	manager.getTransaction().begin();
	Object result = context.proceed();
	manager.getTransaction().commit();

	return result;
    }
}
