package com.example.root.yiplproject.model;

/**
 * Created by root on 8/15/17.
 */
public class Likes {
    private String ownerId;
    private String complainId;
    private String objectId;
    private String likedById;


    public Likes() {
    }

    public Likes(String ownerId, String complainId) {
        this.ownerId = ownerId;
        this.complainId = complainId;
    }

    public String getLikedById() {
        return likedById;
    }

    public void setLikedById(String likedById) {
        this.likedById = likedById;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "ownerId='" + ownerId + '\'' +
                ", complainId='" + complainId + '\'' +
                ", objectId='" + objectId + '\'' +
                ", likedById='" + likedById + '\'' +
                '}';
    }
}
