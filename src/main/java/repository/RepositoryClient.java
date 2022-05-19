package repository;

import dto.*;

import java.util.Collection;

public interface RepositoryClient {
    ResponseDTO<RepositoryDTO> create(CreateUpdateRepositoryDTO createUpdateRepositoryDTO);

    ResponseDTO<Collection<RepositoryDTO>> getAll();

    ResponseDTO<RepositoryDTO> get(String id);

    ResponseDTO<RepositoryDTO> update(String id, CreateUpdateRepositoryDTO createUpdateRepositoryDTO);
    
    ResponseDTO<RepositoryDTO> delete(String id);

    ResponseDTO<SuccessDTO> init(String id);

    ResponseDTO<SuccessDTO> commit(String id);

    ResponseDTO<SuccessDTO> clean(String id);

    ResponseDTO<Integer> count();
}
