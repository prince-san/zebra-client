package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatabaseDTO(
        String id,
        String repository_id,
        String name,
        Collection<StorageDTO> storageDTOS
) {
}
