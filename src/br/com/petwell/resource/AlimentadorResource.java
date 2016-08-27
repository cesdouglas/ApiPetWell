package br.com.petwell.resource;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

import org.apache.tomcat.util.codec.binary.Base64;

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
import br.com.petwell.to.DocCloudantTO;
import br.com.petwell.to.ResponseTO;

@Path("/alimentador")
public class AlimentadorResource {
	
	private AlimentadorDAO dao = new AlimentadorDAOImpl(EntityManagerFactorySingleton
			.getInstance().createEntityManager()); 
	private UsuarioDAO usuarioDao = new UsuarioDAOImpl(EntityManagerFactorySingleton
			.getInstance().createEntityManager()); 
	
	private final String HOST_CLOUDANT = "https://1031a471-fa0d-4678-9253-d455832bfa7e-bluemix.cloudant.com";
	
	private final String LOGIN_CLOUDANT = "1031a471-fa0d-4678-9253-d455832bfa7e-bluemix";
	
	private final String PASS_CLOUDANT = "5940197a80aa72a2f293970de3e32c561db72f2b47005096a178c2554d0cbe5e";
	
	private final String DB_CLOUDANT = "petwell";
	
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
	
	@POST
	@Path("/alimentar/{devCode}")
	@RolesAllowed(value="USER")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alimentar(@PathParam("devCode") int devCode){
		try {
			Alimentador a = dao.buscarPelaHash(devCode, AuthenticationFilter.hashAcesso);
			URL url = new URL(HOST_CLOUDANT + "/" + DB_CLOUDANT);
			HttpURLConnection client = (HttpURLConnection) url.openConnection();
			String userCredentials = LOGIN_CLOUDANT + ":" + PASS_CLOUDANT;
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			client.setRequestProperty("charset", "utf-8");
			client.setRequestMethod("POST");
			client.setRequestProperty ("Authorization", basicAuth);;
			client.setRequestProperty("Content-Type", "application/json");
			client.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(client.getOutputStream());
			wr.write(new Gson().toJson(new DocCloudantTO(a.getDevCode())));
			wr.close();
			int statusCodeHTTP = client.getResponseCode();
			
			if (statusCodeHTTP == HttpURLConnection.HTTP_OK) {
				return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
			}
			return Response.status(Response.Status.OK).entity(ResponseTO.statusTrue()).build();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseTO.statusFalse()).build();
		}catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseTO.statusFalse()).build();
		}
		
	}
}
