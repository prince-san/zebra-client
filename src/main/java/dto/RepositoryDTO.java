package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryDTO(
        String id,
        String name,
        String type,
        Collection<DatabaseDTO> databaseDTOS
) {
}
