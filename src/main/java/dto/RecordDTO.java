package dto;

public record RecordDTO(
        Integer recordPosition,
        String recordPacking,
        String recordIdentifier,
        String recordSchema,
        String recordData
) {
}
