package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CreateStorageDTO;
import dto.ResponseDTO;
import dto.StorageDTO;
import dto.UpdateStorageDTO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import util.HttpRequestBuilder;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;

public class StorageClientImpl implements StorageClient{
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String DEFAULT_API_URI;

    private final String STORAGE_API_URI = "storages/";

    public StorageClientImpl(String default_api_uri) {
        DEFAULT_API_URI = default_api_uri;
    }

    @Override
    public ResponseDTO<StorageDTO> create(String database_id, File file, String addinfo) {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            HttpPost post = HttpRequestBuilder.buildMultipartHttpPost(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI),
                    database_id,
                    file,
                    addinfo
            );
            CloseableHttpResponse response = closeableHttpClient.execute(post);
            return objectMapper.readValue(
                    new String(response.getEntity().getContent().readAllBytes(),
                            StandardCharsets.UTF_8), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<Collection<StorageDTO>> getAll() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<StorageDTO> get(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<StorageDTO> update(String id, UpdateStorageDTO updateStorageDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(updateStorageDTO);
            HttpRequest request = HttpRequestBuilder.buildPutHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI + id),
                    requestBody
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<StorageDTO> delete(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildDeleteHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String download(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<Integer> count() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + STORAGE_API_URI + "count")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
