package com.treinamento.framework.exception;

public class BusinessException extends RuntimeException {
    private final ServiceError error;
    private final int httpStatusCode;

    public BusinessException(String message) {
        this(ServiceError.Builder.instance("ERROR", message).build(), 500, null);
    }

    public BusinessException(String message, Throwable throwable) {
        this(ServiceError.Builder.instance("ERROR", message).build(), 500, throwable);
    }

    public BusinessException(String code, String message, int statusCode) {
        this(ServiceError.Builder.instance(code, message).build(), statusCode, null);
    }

    public BusinessException(String code, String message, String detailed, int statusCode) {
        this(ServiceError.Builder.instance(code, message).withDetailed(detailed).build(), statusCode, null);
    }

    public BusinessException(ServiceError error, int statusCode, Throwable throwable) {
        super(error.getFormattedMessage(), throwable);
        this.error = error;
        this.httpStatusCode = statusCode;
    }

    public ServiceError getError() {
        return error;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
