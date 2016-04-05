package fr.epsi.nosql.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("/redis")
public interface Service {

    @GET
    @Path("/links/{tags}")
    @Produces({"application/json"})
    Response getLinksByTag(@PathParam("tags") String tags) throws IOException;

    @GET
    @Path("/tags")
    @Produces({"application/json"})
    Response getAllTags() throws IOException;

    @GET
    @Path("/links")
    @Produces({"application/json"})
    Response getAllLinks() throws IOException;

    @POST
    @Path("/")
    @Consumes("application/json")
    Response addLink(String link);
}