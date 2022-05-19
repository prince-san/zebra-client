package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import util.HttpRequestBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

public class RepositoryClientImpl implements RepositoryClient {
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String DEFAULT_API_URI;
    private final String REPOSITORY_API_URI = "repositories/";

    public RepositoryClientImpl(String default_api_uri){
        DEFAULT_API_URI = default_api_uri;
    }

    @Override
    public ResponseDTO<RepositoryDTO> create(CreateUpdateRepositoryDTO createUpdateRepositoryDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(createUpdateRepositoryDTO);
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI),
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
    public ResponseDTO<Collection<RepositoryDTO>> getAll() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<RepositoryDTO> get(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<RepositoryDTO> update(String id, CreateUpdateRepositoryDTO createUpdateRepositoryDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(createUpdateRepositoryDTO);
            HttpRequest request = HttpRequestBuilder.buildPutHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id),
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
    public ResponseDTO<RepositoryDTO> delete(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildDeleteHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<SuccessDTO> init(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id + "/init")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<SuccessDTO> commit(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id + "/commit")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<SuccessDTO> clean(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + id + "/clean")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<Integer> count() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + REPOSITORY_API_URI + "count")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
