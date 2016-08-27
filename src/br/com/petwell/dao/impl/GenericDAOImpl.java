package br.com.petwell.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import br.com.petwell.dao.GenericDAO;
import br.com.petwell.exception.DBException;
import br.com.petwell.exception.EntityNotFoundException;

public abstract class GenericDAOImpl<T,K> implements GenericDAO<T, K>{

	protected EntityManager em;
	
	private Class<T> classe;

	@SuppressWarnings("unchecked")//Tira o amarelinho
	public GenericDAOImpl(EntityManager em) {		
		this.em = em;
		this.classe = (Class<T>) ((ParameterizedType) 
			getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	
	@Override
	public void inserir(T entidade) throws DBException {
		try{
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			throw new DBException("Cadastro não realizado");
		}
	}

	@Override
	public void alterar(T entidade) throws DBException {
		try{
			em.getTransaction().begin();
			em.merge(entidade);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			throw new DBException("Alteração não realizada");
		}
	}

	@Override
	public void excluir(K chave) throws DBException, EntityNotFoundException {
		T entidade = buscar(chave);		
		try{
			em.getTransaction().begin();
			em.remove(entidade);
			em.getTransaction().commit();
		}catch(Exception e ){
			e.printStackTrace();
			if (em.getTransaction().isActive()){
				em.getTransaction().rollback();
			}
			throw new DBException("Exclusão não realizada");
		}
	}

	@Override
	public T buscar(K chave) throws EntityNotFoundException {
		T entidade = em.find(classe, chave);
		if (entidade == null){
			throw new EntityNotFoundException("Entidade não encontrada");
		}
		return entidade;
	}
	
	@Override
	public List<T> listar(){
		List<T> lista = em.createQuery("FROM " + classe.getName(), classe).getResultList();
		return lista;
	}
}



