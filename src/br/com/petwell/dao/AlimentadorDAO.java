package br.com.petwell.dao;

import br.com.petwell.entity.Alimentador;
import br.com.petwell.exception.EntityNotFoundException;

public interface AlimentadorDAO extends GenericDAO<Alimentador, Integer>{

	Alimentador buscarPelaHash(int devCode, String hashAcesso) throws EntityNotFoundException;

}
