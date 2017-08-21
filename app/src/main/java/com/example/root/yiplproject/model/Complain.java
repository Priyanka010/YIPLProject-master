package com.example.root.yiplproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 7/18/17.
 */
public class Complain implements Serializable {
    private String to,body,datee,head,id,objectId,ownerId;
    List<Likes> like;

    public Complain(){}

    public String getId() {
        return id;
    }

    public Complain(String to,String head, String body, String date,  String id) {
        this.to = to;
        this.body = body;
        this.datee = date;
        this.head = head;
        this.id = id;
    }

    public Complain(String to,String head, String body, String date,  String id,String ownerId) {
        this.to = to;
        this.body = body;
        this.datee = date;
        this.head = head;
        this.id = id;
        this.ownerId=ownerId;
    }


    public List<Likes> getLike() {
        return like;
    }

    public void setLike(List<Likes> like) {
        this.like = like;
    }



    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String date) {
        this.datee = date;
    }

    @Override
    public String toString() {
        return "Complain{" +
                "to='" + to + '\'' +
                ", body='" + body + '\'' +
                ", datee='" + datee + '\'' +
                ", head='" + head + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
