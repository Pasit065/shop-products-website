package com.web_application.main_website;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason = "Post requests has been failed")
public class PostNewDataBaseDataException extends RuntimeException {
    public PostNewDataBaseDataException(String errorMessageStatus) {
        super(errorMessageStatus);
    }
}