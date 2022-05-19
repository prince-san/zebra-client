package dto;

public enum SearchType {
    PQF("PQF"),
    CQL("CQL");

    private final String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
