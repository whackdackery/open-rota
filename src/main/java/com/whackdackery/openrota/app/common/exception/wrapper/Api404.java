package com.whackdackery.openrota.app.common.exception.wrapper;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Api404 {
    private HttpStatus status;
    private String message;

    public Api404(String message) {
        super();
        this.status = HttpStatus.NOT_FOUND;
        this.message = message;
    }
}
