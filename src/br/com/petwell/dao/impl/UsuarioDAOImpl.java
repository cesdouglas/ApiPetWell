package br.com.petwell.dao.impl;

import javax.persistence.EntityManager;

import br.com.petwell.dao.UsuarioDAO;
import br.com.petwell.entity.Usuario;
import br.com.petwell.exception.EntityNotFoundException;

public class UsuarioDAOImpl extends GenericDAOImpl<Usuario, Integer> implements UsuarioDAO{

	public UsuarioDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public Usuario login(String email, String senha) throws EntityNotFoundException{
		return em.createQuery("FROM Usuario u WHERE u.email = :e AND u.senha = :s", 
				Usuario.class)
				.setParameter("e", email)
				.setParameter("s", senha)
				.getSingleResult();
	}

	@Override
	public Usuario buscarPelaHash(String hashAcesso) throws EntityNotFoundException {
		return em.createQuery("FROM Usuario u WHERE u.hashAcesso = :h", 
				Usuario.class)
				.setParameter("h", hashAcesso)
				.getSingleResult();
	}
}
