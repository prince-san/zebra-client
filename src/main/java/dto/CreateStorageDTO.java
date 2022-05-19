package dto;

public record CreateStorageDTO(
        String database_id,
        byte[] data,
        String addinfo
) {
}
