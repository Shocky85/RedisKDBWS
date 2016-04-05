package fr.epsi.nosql.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisConnector {
    private RedisClient client;
    private RedisConnection connection;

    public RedisConnector() {
        System.out.println("Starting redis connection...");
        try {
            client = new RedisClient("btpvkirjf-redis.services.clever-cloud.com", 1347);
            connection = client.connect();
            connection.auth("0Cz4URs1azHJUPMnP0v");
            System.out.println("Connection etablished !");

        } catch (RedisException e) {
            System.out.println("Error during connection : " + e.toString());
            e.printStackTrace();
        }
    }

    // Add a link
    public void AddLink(String linkProperties) {

        Link link = GetLinkFromJson(linkProperties);
        List<String> tags = link.getTags();
        for(String tag : tags) {
            String tagSet = "tags:" + tag;
            connection.zincrby("tags", 1, tag);
            connection.sadd(tagSet, linkProperties);
        }
        Long timestamp = link.getTimestamp().getTimestamp().getTime();
        connection.zadd("links", timestamp, linkProperties);
    }

    // Get a link with his json properties
    private Link GetLinkFromJson(String linkProperties) {
        // parse json linkProperties to get a link
        ObjectMapper mapper = new ObjectMapper();
        Link link = null;
        try {
            link = mapper.readValue(linkProperties, Link.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return link;
    }

    // Get all links correspond to a list of tags. links are sorted by their occurrence
    public List<String> GetLinksSortedByTags(List<String> tags) {
        String output = "Get links correspond to : " + tags.toString();
        System.out.println(output);

        Map<String, AtomicInteger> links = new HashMap<String, AtomicInteger>();
        for (String tag : tags) {
            String key = "tags:" + tag;
                Set<String> taggedLinks = connection.smembers(key);

                for (String taggedLink : taggedLinks) {
                    
                    links.putIfAbsent(taggedLink, new AtomicInteger(0));
                    links.get(taggedLink).incrementAndGet();
                }
        }
        // trier la map
        List<String> sortedTags = new ArrayList<>();
        links.entrySet()
                .parallelStream()
                .sorted((a,b) -> Integer.compare(a.getValue().get(), b.getValue().get()))
                .forEach((k) -> sortedTags.add(k.getKey()));
        return sortedTags;
    }

    public List<String> getAllTags() {
        System.out.println("Get all tags");
        List<String> tags = connection.zrevrangeWithScores("tags", 0, connection.zcard("tags"));

        return tags;
    }

    public List<String> getAllLinks() {
        System.out.println("Get all tags");
        List<String> tags = connection.zrevrange("links", 0, 9);

        return tags;
    }


    public void CloseClient() {
        client.shutdown();
    }

    public void finalize() {
        CloseClient();
    }
}
