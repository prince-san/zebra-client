package dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UpdateRecordAction {
    UPDATE("update"),
    DELETE("delete");

    private final String action;

    UpdateRecordAction(String action) {
        this.action = action;
    }

    @JsonValue
    public String getAction() {
        return action;
    }
}
