package com.demo;

import static org.junit.Assert.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.junit.BeforeClass;
import org.junit.Test;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static OkHttpClient client;
    static ObjectMapper mapper;
    String BASE_URL = "https://mockk-server.herokuapp.com";

    @BeforeClass
    public static void beforeClass() throws IOException {
        client = new OkHttpClient();
        mapper = new ObjectMapper();
    }

    @Test
    public void simple_get_request() throws IOException {
        Request request = new Request.Builder().url(BASE_URL + "/health").build();

        Call call = client.newCall(request);
        Response response = call.execute();

        
        JsonNode jNode = mapper.readTree(response.body().string());
        System.out.println(jNode.get("Message").asText());

        // refer to https://www.twilio.com/blog/java-json-with-jackson for more working
        // with json

        assertTrue("Response code should be 200", response.code() == 200);
    }

    @Test
    public void get_request_with_params() throws IOException {
        String some_param = "SOME_PARAM";
        Request request = new Request.Builder().url(BASE_URL + "/path_param/" + some_param).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        JsonNode jNode = mapper.readTree(response.body().string());
        System.out.println(jNode.asText());
        String passed_param = jNode.get("params").get("some_param").asText();
        assertTrue("param should be 'some_param'", passed_param.equals(some_param));
        assertTrue("Response code should be 200", response.code() == 200);
    }

    @Test
    public void get_request_with_query_params() throws IOException {
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("sent_body", "This is my body");

        RequestBody body = RequestBody.create(rootNode.toPrettyString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url("https://mockk-server.herokuapp.com/post?some_query=SOME_QUERY")
                .method("POST", body)
                .header("my_headers", "my_custom_header")
                .build();
        Response response = client.newCall(request).execute();
        JsonNode jNode = mapper.readTree(response.body().string());
        System.out.println(jNode.toPrettyString());
        String sent_body = jNode.get("body").get("sent_body").asText();
        
        assertTrue("body should match", sent_body.equals("This is my body"));

        // Like wise we can assert other things as well. 
        assertTrue("response should be 200", response.code() == 200);
    }
}
