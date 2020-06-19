package com.training.cardgame.infra.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class ServiceError implements Serializable {
    private String code;
    private String message;
    private String detailedMessage;

    public ServiceError() {}
    public ServiceError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    @JsonIgnore
    public String getFormattedMessage() {
        return String.format("(%s) -> %s", getCode(), getMessage());
    }

    public static class Builder {
        private final ServiceError error;

        private Builder(String code, String message) {
            this.error = new ServiceError();
            error.setCode(code);
            error.setMessage(message);
        }

        public static Builder instance(String code, String message) {
            return new Builder(code, message);
        }

        public Builder withDetailed(String param) {
            error.setDetailedMessage(param);
            return this;
        }

        public ServiceError build() {
            return error;
        }

    }
}
