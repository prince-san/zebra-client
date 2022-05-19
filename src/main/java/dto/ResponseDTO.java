package dto;

public record ResponseDTO<T>(
        Boolean success,
        T data
) {
}
