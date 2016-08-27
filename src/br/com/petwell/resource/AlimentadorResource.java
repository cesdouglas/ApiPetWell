package br.com.petwell.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.petwell.bo.AlimentadorBO;
import br.com.petwell.config.AuthenticationFilter;
import br.com.petwell.dao.AlimentadorDAO;
import br.com.petwell.dao.UsuarioDAO;
import br.com.petwell.dao.impl.AlimentadorDAOImpl;
import br.com.petwell.dao.impl.UsuarioDAOImpl;
import br.com.petwell.entity.Alimentador;
import br.com.petwell.exception.EntityNotFoundException;
import br.com.petwell.singleton.EntityManagerFactorySingleton;
import br.com.petwell.to.AlimentadorTO;
import br.com.petwell.to.ResponseTO;

@Path("/alimentador")
public class AlimentadorResource {
	
	private AlimentadorDAO dao = new AlimentadorDAOImpl(EntityManagerFactorySingleton
			.getInstance().createEntityManager()); 
	private UsuarioDAO usuarioDao = new UsuarioDAOImpl(EntityManagerFactorySingleton
			.getInstance().createEntityManager()); 
	
	@POST
	@RolesAllowed(value="USER")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrar(String json){
		try {
			AlimentadorTO to = new Gson().fromJson(json, AlimentadorTO.class);
			AlimentadorBO.cadastrar(to, dao, usuarioDao);
			return Response.status(Response.Status.CREATED).entity(ResponseTO.statusTrue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@GET
	@Path("/{devCode}")
	@RolesAllowed(value="USER")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("devCode") int devCode){
		try {
			Alimentador a = dao.buscarPelaHash(devCode, AuthenticationFilter.hashAcesso);
			Gson gson = new GsonBuilder()
				    .excludeFieldsWithoutExposeAnnotation()
				    .create();
			return Response.status(Response.Status.OK).entity(gson.toJson(a)).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseTO.statusFalse()).build();
		}catch (Exception e){
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ResponseTO.statusFalse()).build();
		}
		
		
	}
	
	@PUT
	@Path("/{devCode}")
	@RolesAllowed(value="USER")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterar(@PathParam("devCode") int devCode, String json){
		try {
			AlimentadorTO to = new Gson().fromJson(json, AlimentadorTO.class);
			AlimentadorBO.alterar(devCode, to, dao, usuarioDao);
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseTO.statusFalse()).build();
		}
	}
	
	@DELETE
	@Path("/{devCode}")
	@RolesAllowed(value="USER")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response apagar(@PathParam("devCode") int devCode){
		try {
			Alimentador a = dao.buscarPelaHash(devCode, AuthenticationFilter.hashAcesso);
			dao.excluir(a.getCodigo());
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseTO.statusFalse()).build();
		}
	}
}
