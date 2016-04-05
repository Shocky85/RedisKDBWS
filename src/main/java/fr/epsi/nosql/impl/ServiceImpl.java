package fr.epsi.nosql.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epsi.nosql.dao.RedisConnector;
import fr.epsi.nosql.service.Service;
import org.jboss.resteasy.logging.Logger;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {

    private Logger logger = Logger.getLogger(ServiceImpl.class);
    private static RedisConnector dao;

    public Response getLinksByTag(String tags) throws IOException {
        System.out.println(tags);
        ObjectMapper mapper = new ObjectMapper();
        List<String> navigation = mapper.readValue(
                tags,
                mapper.getTypeFactory().constructCollectionType(
                        List.class, String.class));
        List<String> list =getDao().GetLinksSortedByTags(navigation);

        String json = mapper.writeValueAsString(list);
        return Response.status(200).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .entity(json).build();
    }

    public Response getAllTags() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> list =getDao().getAllTags();
        String json = mapper.writeValueAsString(list);
        return Response.status(200).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .entity(json).build();
    }

    public Response getAllLinks() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> list =getDao().getAllLinks();
        String json = mapper.writeValueAsString(list);
        return Response.status(200).header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .entity(json).build();
    }

    @Override
    public Response addLink(String link) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        try {
            getDao().AddLink(link);
            status = Response.Status.CREATED;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Response.status(status).build();
    }

    private RedisConnector getDao() {
        System.out.println(dao);
        if (this.dao == null) {
            this.dao = new RedisConnector();
        }
        return this.dao;
    }
}
