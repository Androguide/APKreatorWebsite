package com.androguide.apkreator.helpers.gplus;

import java.io.Serializable;
import java.util.List;

public class Stream implements Serializable {
    private String user;
    private List<Post> posts;

    public Stream(String user, List<Post> posts) {
        this.user = user;
        this.posts = posts;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }
}