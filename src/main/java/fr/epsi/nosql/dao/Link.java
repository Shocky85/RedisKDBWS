package fr.epsi.nosql.dao;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.security.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeremy on 21/03/2016.
 */
public class Link {
    private String href;
    private String description;
    private String extended;
    private String meta;
    private String hash;
    private Timestamp timestamp;
    private List<String> tagsList;


    @JsonCreator
    public Link(Map<String,Object> delegate) {
        href = (String)delegate.get("href");
        description = (String)delegate.get("description");
        extended = (String)delegate.get("extended");
        meta = (String)delegate.get("meta");
        hash = (String)delegate.get("hash");
        timestamp = (Timestamp)delegate.get("time");
        String stringTags = (String)delegate.get("tags");
        String[] tagsTab = stringTags.split(" ");
        for(String tag : tagsTab) {
            tagsList.add(tag);
        }
    }
    public Link(String href, String description, String extended, String meta, String hash, Timestamp timestamp, List<String> tags) {
        this.href = href;
        this.description = description;
        this.extended = extended;
        this.meta = meta;
        this.hash = hash;
        this.timestamp = timestamp;
        this.tagsList = tags;
    }

    public String getHref() {
        return href;
    }

    public String getDescription() {
        return description;
    }

    public String getExtended() {
        return extended;
    }

    public String getMeta() {
        return meta;
    }

    public String getHash() {
        return hash;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public List<String> getTags() {
        return tagsList;
    }
}
