package dto;

public record UpdateRecordDTO(
        UpdateRecordAction updateRecordAction,
        String record,
        Boolean commitEnabled
) {
}
