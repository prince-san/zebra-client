package migration;

import dto.*;
import repository.DatabaseClient;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class MigrationExecutorImpl implements MigrationExecutor {
    private final List<DatabaseDTO> databasesToMigrate;
    private final DatabaseDTO targetDatabase;
    private final TimestampStorage timestampStorage;
    private final DatabaseClient databaseClient;

    public MigrationExecutorImpl(List<DatabaseDTO> databasesToMigrate,
                                 DatabaseDTO targetDatabase,
                                 TimestampStorage timestampStorage,
                                 DatabaseClient databaseClient) {
        this.databasesToMigrate = databasesToMigrate;
        this.targetDatabase = targetDatabase;
        this.timestampStorage = timestampStorage;
        this.databaseClient = databaseClient;
    }

    @Override
    public void migrate() {
        for (DatabaseDTO database : databasesToMigrate) {
            Instant databaseTimestamp = timestampStorage.getTimestamp(database.id())
                    .orElse(Instant.parse("1970-01-01T00:00:00Z"));
            Instant currentTimestamp = Instant.now();

            ResponseDTO<ResponseDTO<ScanResponseDTO>> createdSearchResult = databaseClient
                    .findAllWithCreatedDateIsAfter(database.id(), databaseTimestamp);
            ResponseDTO<ResponseDTO<ScanResponseDTO>> modifiedSearchResult = databaseClient
                    .findAllWithModifiedDateIsAfter(database.id(), databaseTimestamp);

            String searchRequest = "";
            if (createdSearchResult.success() && modifiedSearchResult.success()) {
                searchRequest = buildSearchRequest(createdSearchResult, modifiedSearchResult);
            } else if (createdSearchResult.success()) {
                searchRequest = buildSearchRequest(createdSearchResult, true);
            } else if (modifiedSearchResult.success()) {
                searchRequest = buildSearchRequest(modifiedSearchResult, false);
            } else {
                continue;
            }
            var migrationRecordsResponse = databaseClient
                    .search(database.id(), new SearchRequestDTO(
                            SearchType.PQF,
                            searchRequest,
                            null,
                            null,
                            "dc",
                            null
                    ));

            if (!migrationRecordsResponse.success()) {
                timestampStorage.updateTimestamp(database.id(), currentTimestamp);
                continue;
            }

            migrationRecordsResponse.data().data().records().stream()
                    .map(RecordDTO::recordData)
                    .forEach(it -> databaseClient
                            .updateRecord(targetDatabase.id(), buildUpdateRecordRequestDTO(it)));

            timestampStorage.updateTimestamp(database.id(), currentTimestamp);
        }
    }

    private String prepareTimestampQuery(ScanResponseDTO response) {
        return response.terms().stream().map(TermDTO::displayTerm)
                .collect(Collectors.joining(" ", "\"", "\""));
    }

    private String buildSearchRequest(ResponseDTO<ResponseDTO<ScanResponseDTO>> createdResult,
                                      ResponseDTO<ResponseDTO<ScanResponseDTO>> modifiedResponse) {
        return "@or @1=1011 @4=106 " + prepareTimestampQuery(createdResult.data().data())
                + " @1=1012 @4=106 " + prepareTimestampQuery(modifiedResponse.data().data());
    }

    private String buildSearchRequest(ResponseDTO<ResponseDTO<ScanResponseDTO>> result, boolean isCreated) {
        var searchQuery = prepareTimestampQuery(result.data().data());
        if (isCreated) {
            return "@1=1011 @4=106 " + searchQuery;
        }
        return "@1=1012 @4=106 " + searchQuery;
    }

    private UpdateRecordDTO buildUpdateRecordRequestDTO(String recordData) {
        return new UpdateRecordDTO(
                UpdateRecordAction.UPDATE,
                recordData,
                true
        );
    }
}
