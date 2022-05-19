import dto.*;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import repository.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZebraApiTest {
    String uri = "http://localhost:3000/api/v1/";
    private final RepositoryClient repositoryClient = new RepositoryClientImpl(uri);
    private final DatabaseClient databaseClient = new DatabaseClientImpl(uri);
    private final StorageClient storageClient = new StorageClientImpl(uri);

    @Test
    public void repositoryIntegrationTest() {
        ResponseDTO<RepositoryDTO> repositoryResponse = repositoryClient.create(new CreateUpdateRepositoryDTO(
                "sampleRepo", "fit.nsu.ru"));
        assertTrue(repositoryResponse.success());

        RepositoryDTO repository = repositoryResponse.data();
        assertTrue(repositoryClient.init(repository.id()).success());

        ResponseDTO<DatabaseDTO> databaseResponse = databaseClient.create(new CreateUpdateDatabaseDTO(
                repository.id(),
                "sampleDb"
        ));
        assertTrue(databaseResponse.success());

        assertTrue(repositoryClient.commit(repository.id()).success());

        DatabaseDTO database = databaseResponse.data();

        File file = new File("src/main/resources/dataset.xml");
        ResponseDTO<StorageDTO> storageResponse = storageClient.create(
                        database.id(),
                        file,
                        ""
        );
        assertTrue(storageResponse.success());

        StorageDTO storage = storageResponse.data();

        assertTrue(databaseClient.updateStorage(
                database.id(),
                storage.id()
        ).success());

        var searchResult = databaseClient.search(database.id(), new SearchRequestDTO(
                SearchType.PQF,
                "@1=7 9780415190732",
                null,
                null,
                "dc",
                null
        ));
        assertTrue(searchResult.success());
        System.out.println(searchResult.data().data().records());

        var scanResult = databaseClient.scan(database.id(), new ScanRequestDTO(
                SearchType.CQL,
                "dc.title = Rura",
                null,
                null
        ));
        assertTrue(scanResult.success());
        System.out.println(scanResult.data().data().terms());

        var storageDeleteResult = storageClient.delete(storage.id());
        assertTrue(storageDeleteResult.success());
        assertEquals(0, storageClient.count().data());

        var databaseDeleteResult = databaseClient.delete(database.id());
        assertTrue(databaseDeleteResult.success());
        assertEquals(0, databaseClient.count().data());

        var repositoryDeleteResult = repositoryClient.delete(repository.id());
        assertTrue(repositoryDeleteResult.success());
        assertEquals(0, repositoryClient.count().data());
    }
}
