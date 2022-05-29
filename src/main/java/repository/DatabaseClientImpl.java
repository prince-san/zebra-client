package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import migration.Formatter;
import org.apache.http.client.utils.URIBuilder;
import util.HttpRequestBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Collection;

public class DatabaseClientImpl implements DatabaseClient {

    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String DEFAULT_API_URI;
    private final String DATABASE_API_URI = "databases/";
    private final Formatter formatter = Formatter.INSTANCE;

    public DatabaseClientImpl(String default_api_uri) {
        DEFAULT_API_URI = default_api_uri;
    }

    @Override
    public ResponseDTO<DatabaseDTO> create(CreateUpdateDatabaseDTO createUpdateDatabaseDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(createUpdateDatabaseDTO);
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI),
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
    public ResponseDTO<Collection<DatabaseDTO>> getAll() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<DatabaseDTO> get(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<DatabaseDTO> update(String id, CreateUpdateDatabaseDTO createUpdateDatabaseDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(createUpdateDatabaseDTO);
            HttpRequest request = HttpRequestBuilder.buildPutHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id),
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
    public ResponseDTO<DatabaseDTO> delete(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildDeleteHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<SuccessDTO> updateStorage(String id, String storage_id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id + "/update/" + storage_id)
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<SuccessDTO> updateRecord(String id, UpdateRecordDTO updateRecordDTO) {
        try {
            String requestBody = objectMapper.writeValueAsString(updateRecordDTO);
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id + "/updateRecord"),
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
    public ResponseDTO<SuccessDTO> drop(String id) {
        try {
            HttpRequest request = HttpRequestBuilder.buildPostHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + id + "/drop")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<ResponseDTO<SearchResponseDTO>> search(String id, SearchRequestDTO searchRequestDTO) {
        try {
            URI uri = new URI(DEFAULT_API_URI + DATABASE_API_URI + id + "/search");
            URIBuilder uriBuilder = new URIBuilder(uri).addParameter("query", searchRequestDTO.query());
            if (searchRequestDTO.searchType() != null) uriBuilder.addParameter("type", searchRequestDTO.searchType().getType());
            if (searchRequestDTO.startRecord() != null) uriBuilder.addParameter("startRecord", searchRequestDTO.startRecord().toString());
            if (searchRequestDTO.maximumRecords() != null) uriBuilder.addParameter("maximumRecords", searchRequestDTO.maximumRecords().toString());
            if (searchRequestDTO.recordSchema() != null) uriBuilder.addParameter("recordSchema", searchRequestDTO.recordSchema());
            if (searchRequestDTO.sortKeys() != null) uriBuilder.addParameter("sortKeys", searchRequestDTO.sortKeys());
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(uriBuilder.build());
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<ResponseDTO<ScanResponseDTO>> scan(String id, ScanRequestDTO scanRequestDTO) {
        try {
            URI uri = new URI(DEFAULT_API_URI + DATABASE_API_URI + id + "/scan");
            URIBuilder uriBuilder = new URIBuilder(uri).addParameter("scanClause", scanRequestDTO.scanClause());
            if (scanRequestDTO.searchType() != null) uriBuilder.addParameter("type", scanRequestDTO.searchType().getType());
            if (scanRequestDTO.number() != null) uriBuilder.addParameter("number", scanRequestDTO.number().toString());
            if (scanRequestDTO.position() != null) uriBuilder.addParameter("position", scanRequestDTO.position().toString());
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(uriBuilder.build());
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<ResponseDTO<ScanResponseDTO>> findAllWithCreatedDateIsAfter(String id, Instant date) {
        try {
            var formattedDate = formatter.formatInstant(date);
            return scan(id, new ScanRequestDTO(
                    SearchType.PQF,
                    "@1=1011 @6=3 " + formattedDate,
                    null,
                    null
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseDTO<>(false, null);
    }

    @Override
    public ResponseDTO<ResponseDTO<ScanResponseDTO>> findAllWithModifiedDateIsAfter(String id, Instant date) {
        try {
            var formattedDate = formatter.formatInstant(date);
            return scan(id, new ScanRequestDTO(
                    SearchType.PQF,
                    "@1=1012 @6=3 " + formattedDate,
                    null,
                    null
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDTO<>(false, null);
    }

    @Override
    public ResponseDTO<Integer> count() {
        try {
            HttpRequest request = HttpRequestBuilder.buildGetHttpRequest(
                    new URI(DEFAULT_API_URI + DATABASE_API_URI + "count")
            );
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() { });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
