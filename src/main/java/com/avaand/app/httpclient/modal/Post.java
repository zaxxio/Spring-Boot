package com.avaand.app.httpclient.modal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;

    // getters and setters


    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
