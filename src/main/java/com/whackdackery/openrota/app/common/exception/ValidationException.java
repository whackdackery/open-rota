package com.whackdackery.openrota.app.common.exception;

import com.whackdackery.openrota.app.common.exception.wrapper.ValidationError;

import java.util.List;

public class ValidationException extends RuntimeException {

    List<ValidationError> errors;
    public ValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }
}
