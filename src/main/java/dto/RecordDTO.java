package dto;

public record RecordDTO(
        Integer recordPosition,
        String recordPacking,
        String recordIdentifier,
        String recordSchema,
        Object recordData
) {
}
