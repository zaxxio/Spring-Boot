package com.avaand.app.httpclient;

import com.avaand.app.httpclient.modal.Post;

public class Driver {

    public static void main(String[] args) {
        Octo reflectFit = new Octo("https://jsonplaceholder.typicode.com");
        ApiService apiService = reflectFit.create(ApiService.class);
        Post getResponse = apiService.getPost("posts",2);
        System.out.println(getResponse);
        //String postResponse = apiService.postUsername("myRequestBody");

    }

}
