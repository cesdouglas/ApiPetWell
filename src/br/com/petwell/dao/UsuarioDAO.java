package br.com.petwell.dao;
import br.com.petwell.entity.Usuario;
import br.com.petwell.exception.EntityNotFoundException;

public interface UsuarioDAO extends GenericDAO<Usuario, Integer>{
	
	Usuario login(String email, String senha) throws EntityNotFoundException;

	Usuario buscarPelaHash(String hashAcesso) throws EntityNotFoundException;
}
