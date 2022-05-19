package dto;

public record SearchRequestDTO(
        SearchType searchType,
        String query,
        Integer startRecord,
        Integer maximumRecords,
        String recordSchema,
        String sortKeys
) {
}
