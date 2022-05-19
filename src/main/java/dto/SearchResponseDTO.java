package dto;

import javax.tools.Diagnostic;
import java.util.Collection;

public record SearchResponseDTO(
        Integer numberOfRecords,
        Collection<RecordDTO> records,
        Collection<DiagnosticDTO> diagnostics
) {
}
