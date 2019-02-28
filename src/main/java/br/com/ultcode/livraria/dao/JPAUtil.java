package br.com.ultcode.livraria.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("livraria");
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void closeEntityManagerFactory() {
		emf.close();
	}
}
