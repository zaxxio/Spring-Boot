package com.avaand.app.httpclient;

import com.avaand.app.httpclient.http.GET;
import com.avaand.app.httpclient.http.POST;
import com.avaand.app.httpclient.http.Path;
import com.avaand.app.httpclient.modal.Post;
import org.springframework.messaging.handler.annotation.Header;

public interface ApiService {
    @GET("/{posts}/{id}")
    Post getPost(@Path("posts") String action, @Path("id") Integer postId);
    @POST("/username")
    String postUsername(String name);
    @GET("/endpoint")
    String getEndpoint(@Header("Accept") String accept, @Header("User-Agent") String userAgent);

}
