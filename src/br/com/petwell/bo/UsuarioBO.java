package br.com.petwell.bo;

import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.petwell.config.AuthenticationFilter;
import br.com.petwell.dao.UsuarioDAO;
import br.com.petwell.dao.impl.UsuarioDAOImpl;
import br.com.petwell.entity.Usuario;
import br.com.petwell.exception.DBException;
import br.com.petwell.exception.EntityNotFoundException;
import br.com.petwell.singleton.EntityManagerFactorySingleton;
import br.com.petwell.to.UsuarioTO;
import br.com.petwell.util.Util;

public abstract class UsuarioBO {
	
	public static boolean autenticarHashAcesso(String hashAcesso){
		try {
			EntityManager em = EntityManagerFactorySingleton
					.getInstance().createEntityManager();
			UsuarioDAO dao = new UsuarioDAOImpl(em);
			dao.buscarPelaHash(hashAcesso);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String criarHash(String email, String senha){
		String hash = email + senha + Calendar.getInstance();
		return Util.cryptWithMD5(hash);
	}

	public static void cadastrar(UsuarioTO to, UsuarioDAO dao) throws DBException{
		Usuario u = new Usuario();
		u.setAlimentadores(to.getAlimentadores());
		u.setSenha(Util.cryptWithMD5(to.getSenha()));
		dao.inserir(u);
	}
	
	public static void alterar(UsuarioTO to, UsuarioDAO dao) throws DBException, EntityNotFoundException{
		Usuario uBuscado = dao.buscarPelaHash(AuthenticationFilter.hashAcesso);
		uBuscado.setEmail(to.getEmail());
		uBuscado.setNome(to.getNome());
		uBuscado.setSenha(Util.cryptWithMD5(to.getSenha()));
		dao.alterar(uBuscado);
	}
	
	public static void apagar(UsuarioDAO dao) throws DBException, EntityNotFoundException{
		Usuario u = dao.buscarPelaHash(AuthenticationFilter.hashAcesso);
		dao.excluir(u.getCodigo());
	}
	
	public static String login(UsuarioTO to, UsuarioDAO dao) throws DBException, EntityNotFoundException{
		Usuario uBuscado = dao.login(to.getEmail(), Util.cryptWithMD5(to.getSenha()));
		String hashAcesso = UsuarioBO.criarHash(uBuscado.getEmail(), uBuscado.getSenha());
		uBuscado.setHashAcesso(hashAcesso);
		dao.alterar(uBuscado);
		return hashAcesso;
	}
}
