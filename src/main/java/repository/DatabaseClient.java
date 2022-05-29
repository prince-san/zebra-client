package repository;

import dto.*;

import java.time.Instant;
import java.util.Collection;

public interface DatabaseClient {

    ResponseDTO<DatabaseDTO> create(CreateUpdateDatabaseDTO createUpdateDatabaseDTO);

    ResponseDTO<Collection<DatabaseDTO>> getAll();

    ResponseDTO<DatabaseDTO> get(String id);

    ResponseDTO<DatabaseDTO> update(String id, CreateUpdateDatabaseDTO createUpdateDatabaseDTO);

    ResponseDTO<DatabaseDTO> delete(String id);

    ResponseDTO<SuccessDTO> updateStorage(String id, String storage_id);

    ResponseDTO<SuccessDTO> updateRecord(String id, UpdateRecordDTO updateRecordDTO);

    ResponseDTO<SuccessDTO> drop(String id);

    ResponseDTO<ResponseDTO<SearchResponseDTO>> search(String id, SearchRequestDTO searchRequestDTO);

    ResponseDTO<ResponseDTO<ScanResponseDTO>> scan(String id, ScanRequestDTO scanRequestDTO);

    ResponseDTO<ResponseDTO<ScanResponseDTO>> findAllWithCreatedDateIsAfter(String id, Instant date);

    ResponseDTO<ResponseDTO<ScanResponseDTO>> findAllWithModifiedDateIsAfter(String id, Instant date);

    ResponseDTO<Integer> count();
}
