package br.com.petwell.singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {

	private static EntityManagerFactory factory;
	
	//Construtor private
	private EntityManagerFactorySingleton() {}
	
	public static EntityManagerFactory getInstance(){
		if (factory == null){
			factory = Persistence
				.createEntityManagerFactory("CLIENTE_ORACLE");
		}
		return factory;
	}
	
//	private static EntityManager factory;
//	
//	//Construtor private
//	private EntityManagerFactorySingleton() {}
//	
//	public static EntityManager getInstance(){
//		if (factory == null){
//			factory = Persistence
//				.createEntityManagerFactory("CLIENTE_ORACLE")
//				.createEntityManager();
//		}
//		return factory;
//	}
	
}
