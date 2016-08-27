package br.com.petwell.bo;

import br.com.petwell.config.AuthenticationFilter;
import br.com.petwell.dao.AlimentadorDAO;
import br.com.petwell.dao.UsuarioDAO;
import br.com.petwell.entity.Alimentador;
import br.com.petwell.exception.DBException;
import br.com.petwell.exception.EntityNotFoundException;
import br.com.petwell.to.AlimentadorTO;

public abstract class AlimentadorBO {
	
	public static void cadastrar(AlimentadorTO to, AlimentadorDAO dao, UsuarioDAO usuarioDao) throws EntityNotFoundException, DBException{
		Alimentador a = new Alimentador();
		a.setDevCode(to.getDevCode());
		a.setNome(to.getNome());
		a.setUsuario(usuarioDao.buscarPelaHash(AuthenticationFilter.hashAcesso));
		dao.inserir(a);
	}
	
	public static void alterar(int devCode, AlimentadorTO to, AlimentadorDAO dao, UsuarioDAO usuarioDao) throws EntityNotFoundException, DBException{
		Alimentador a = dao.buscarPelaHash(devCode, AuthenticationFilter.hashAcesso);
		a.setDevCode(to.getDevCode());
		a.setNome(to.getNome());
		dao.alterar(a);
	}
}
