package br.com.petwell.resource;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.petwell.bo.UsuarioBO;
import br.com.petwell.config.AuthenticationFilter;
import br.com.petwell.dao.UsuarioDAO;
import br.com.petwell.dao.impl.UsuarioDAOImpl;
import br.com.petwell.entity.Usuario;
import br.com.petwell.exception.EntityNotFoundException;
import br.com.petwell.singleton.EntityManagerFactorySingleton;
import br.com.petwell.to.ResponseTO;
import br.com.petwell.to.UsuarioTO;

@Path("/usuario")
public class UsuarioResource {
	
	EntityManager em = EntityManagerFactorySingleton
			.getInstance().createEntityManager();
	private UsuarioDAO dao = new UsuarioDAOImpl(em); 
	
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(String json){
		try {
			UsuarioTO to = new Gson().fromJson(json, UsuarioTO.class);
			UsuarioBO.cadastrar(to, dao);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ResponseTO.statusFalse()).build();
		}
		
		return Response.status(Response.Status.CREATED).entity(ResponseTO.statusTrue()).build();
	}
	
	@GET
	@RolesAllowed(value="USER")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarUsuario(){
		try {
			Usuario u = dao.buscarPelaHash(AuthenticationFilter.hashAcesso);
			Gson gson = new GsonBuilder()
				    .excludeFieldsWithoutExposeAnnotation()
				    .create();
			return Response.status(Response.Status.OK).entity(gson.toJson(u)).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseTO.statusFalse()).build();
		}catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@PUT
	@RolesAllowed(value="USER")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterar(String json){
		try {
			UsuarioTO to = new Gson().fromJson(json, UsuarioTO.class);
			UsuarioBO.alterar(to, dao);
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_MODIFIED).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@DELETE
	@RolesAllowed(value="USER")
	@Produces(MediaType.APPLICATION_JSON)
	public Response apagar(){
		try {
			UsuarioBO.apagar(dao);
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@POST
	@PermitAll
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String credenciais){
		try {
			UsuarioTO to = new Gson().fromJson(credenciais, UsuarioTO.class);
			String hashAcesso = UsuarioBO.login(to, dao);
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue(hashAcesso)).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseTO.statusFalse()).build();
		} catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@POST
	@RolesAllowed("USER")
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(){
		try {
			Usuario u = dao.buscarPelaHash(AuthenticationFilter.hashAcesso);
			u.setHashAcesso(null);
			dao.alterar(u);
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseTO.statusFalse()).build();
		} catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ResponseTO.statusFalse()).build();
		}
	}
}
