package com.whackdackery.openrota.app.common.exception.wrapper;

import lombok.Data;

@Data
public class ValidationError {
    private final String message;
}
