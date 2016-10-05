package br.com.petwell.dao.impl;

import java.util.List;

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
		return em.createQuery("FROM Alimentador a WHERE a.devCode = :d AND a.usuario.hashAcesso = :h", 
				Alimentador.class)
				.setParameter("d", devCode)
				.setParameter("h", hashAcesso)
				.getSingleResult();
	}

	@Override
	public List<Alimentador> listar(String hashAcesso) throws EntityNotFoundException {
		return em.createQuery("FROM Alimentador a WHERE a.usuario.hashAcesso = :h", Alimentador.class).setParameter("h", hashAcesso).getResultList();
	}

}
