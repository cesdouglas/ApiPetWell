package br.com.petwell.dao.impl;

import javax.persistence.EntityManager;

import br.com.petwell.dao.AlimentadorDAO;
import br.com.petwell.entity.Alimentador;
import br.com.petwell.exception.EntityNotFoundException;

public class AlimentadorDAOImpl extends GenericDAOImpl<Alimentador, Integer> implements AlimentadorDAO{

	public AlimentadorDAOImpl(EntityManager em) {
		super(em);
	}

	@Override
	public Alimentador buscarPelaHash(int devCode, String hashAcesso) throws EntityNotFoundException{
		return em.createQuery("FROM Alimentador a WHERE a.devCode = :d AND a.Usuario.hashAcesso = :h", 
				Alimentador.class)
				.setParameter("d", devCode)
				.setParameter("h", hashAcesso)
				.getSingleResult();
	}
	
}
