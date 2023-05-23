package com.avaand.app.httpclient;

import com.avaand.app.httpclient.converter.GsonConverter;
import com.avaand.app.httpclient.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Octo {

    private String baseUrl;
    private GsonConverter gsonConverter;

    public Octo(String baseUrl){
        this.baseUrl = baseUrl;
        this.gsonConverter = new GsonConverter();
    }

    public <T> T create(Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                Parameter[] parameters = method.getParameters();
//                for (int i = 0; i < parameters.length; i++) {
//                    Parameter parameter = parameters[i];
//
//                    // Check if the parameter has the Header annotation
//                    if (parameter.isAnnotationPresent(Header.class)) {
//                        Header header = parameter.getAnnotation(Header.class);
//                        String headerName = header.value();
//                        String headerValue = (String) args[i]; // Assuming that header values are always strings
//
//                        // Set header on your HttpURLConnection or OkHttp Request
//                        connection.setRequestProperty(headerName, headerValue);
//                    }
//                }
                if (method.isAnnotationPresent(GET.class)){
                    GET get = method.getAnnotation(GET.class);
                    String url = baseUrl + get.value();

                    Parameter[] parameters = method.getParameters();

                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].isAnnotationPresent(Path.class)){
                            Path path = parameters[i].getAnnotation(Path.class);
                            url = url.replace("{" + path.value() + "}", (String) args[i]);
                        }
                        if (parameters[i].isAnnotationPresent(Query.class)){
                            Query query = parameters[i].getAnnotation(Query.class);
                            url += "?" + query.value() + "=" + args[i];
                        }
                    }

                    URL requestUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

                    // optional default is GET
                    connection.setRequestMethod("GET");

                    // add request header
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Object convertedResponse = response;
                    if (gsonConverter != null && method.getReturnType() != String.class) {
                        Type returnType = method.getGenericReturnType();
                        convertedResponse = gsonConverter.fromJson(String.valueOf(response), returnType);
                        return convertedResponse;
                    }

                    // return result
                    return response.toString();
                }else if (method.isAnnotationPresent(POST.class)){

                    POST post = method.getAnnotation(POST.class);
                    String url = baseUrl + post.value();
                    String body = null;

                    // Handle @Body annotation
                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].isAnnotationPresent(Body.class)) {
                            body = (String) args[i];
                        }
                    }

                    URL requestUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

                    // optional default is GET
                    connection.setRequestMethod("POST");

                    // add request header
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = connection.getResponseCode();
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // return result
                    return response.toString();
                }
                return new IllegalArgumentException("Does not match.");
            }
        });
    }

}
