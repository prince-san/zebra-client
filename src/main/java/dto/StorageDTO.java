package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StorageDTO(
        String id,
        String database_id,
        String uuidfilename,
        String filename,
        Integer filesize,
        String addinfo
) {
}
