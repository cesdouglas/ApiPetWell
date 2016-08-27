package br.com.petwell.dao;

import java.util.List;
import br.com.petwell.exception.DBException;
import br.com.petwell.exception.EntityNotFoundException;

public interface GenericDAO<T,K> {

	public void inserir(T entidade) throws DBException;
	
	public void alterar(T entidade) throws DBException;
	
	public void excluir(K chave) throws DBException, EntityNotFoundException;
	
	public T buscar(K chave) throws EntityNotFoundException;
	
	public List<T> listar() throws DBException;
}