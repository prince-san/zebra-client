package repository;

import dto.CreateStorageDTO;
import dto.ResponseDTO;
import dto.StorageDTO;
import dto.UpdateStorageDTO;

import java.io.File;
import java.util.Collection;

public interface StorageClient {
    ResponseDTO<StorageDTO> create(String database_id, File file, String addinfo);

    ResponseDTO<Collection<StorageDTO>> getAll();

    ResponseDTO<StorageDTO> get(String id);

    ResponseDTO<StorageDTO> update(String id, UpdateStorageDTO updateStorageDTO);

    ResponseDTO<StorageDTO> delete(String id);

    String download(String id);

    ResponseDTO<Integer> count();
}
