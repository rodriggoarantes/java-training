package com.totvs.treinamento.twitter.infra.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    private final ServiceError error;
    private final int httpStatusCode;

    public BusinessException(String message) {
        this(ServiceError.Builder.instance("ERROR", message).build(), HttpStatus.BAD_REQUEST.value());
    }

    public BusinessException(String code, String message, int httpStatusCode) {
        this(ServiceError.Builder.instance(code, message).build(), httpStatusCode);
    }

    public BusinessException(String code, String message, String detailed, int httpStatusCode) {
        this(ServiceError.Builder.instance(code, message).withDetailed(detailed).build(), httpStatusCode);
    }

    public BusinessException(ServiceError error, int httpStatusCode) {
        super(error.getFormattedMessage());
        this.error = error;
        this.httpStatusCode = httpStatusCode;
    }

    public BusinessException(ServiceError error, Throwable cause, int httpStatusCode) {
        super(error.getFormattedMessage(), cause);
        this.error = error;
        this.httpStatusCode = httpStatusCode;
    }

    public ServiceError getError() {
        return error;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
