package util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestBuilder {

    public static HttpRequest buildPostHttpRequest(URI uri, String requestBody) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public static HttpRequest buildPostHttpRequest(URI uri) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    public static HttpRequest buildGetHttpRequest(URI uri) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();
    }

    public static HttpRequest buildPutHttpRequest(URI uri, String requestBody) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .setHeader("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public static HttpRequest buildDeleteHttpRequest(URI uri) {
        return HttpRequest
                .newBuilder()
                .uri(uri)
                .DELETE()
                .build();
    }

    public static HttpPost buildMultipartHttpPost(URI uri, String database_id, File file, String addinfo) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(MultipartEntityBuilder.create()
                .addTextBody("database_id", database_id)
                .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName())
                .addTextBody("addinfo", addinfo).build());
        return post;
    }
}
