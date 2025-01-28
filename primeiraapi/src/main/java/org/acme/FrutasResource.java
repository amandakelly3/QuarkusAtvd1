package org.acme;

import java.util.List;

import org.acme.model.Fruta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/frutas")
public class FrutasResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrutasResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fruta> list() {
    	LOGGER.info("Listando todas as fruta");
        return Fruta.listAll();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
    	LOGGER.info("Fruta por id");
        Fruta fruta = Fruta.findById(id);
        if (fruta == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(fruta).build();
    }
    
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Fruta fruta) {
    	LOGGER.info("Criando nova fruta");
        fruta.persist();
        return Response.status(Response.Status.CREATED).entity(fruta).build();
    }
    
    @DELETE
    @Transactional
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
    	LOGGER.info("Deletando fruta");
        boolean deleted = Fruta.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
