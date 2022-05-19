package dto;

import java.util.Collection;

public record ScanResponseDTO(
        Collection<TermDTO> terms,
        Collection<DiagnosticDTO> diagnostics
) {
}
