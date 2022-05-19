package dto;

public record ScanRequestDTO(
        SearchType searchType,
        String scanClause,
        Integer number,
        Integer position
) {
}
