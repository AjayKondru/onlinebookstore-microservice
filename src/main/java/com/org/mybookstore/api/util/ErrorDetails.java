package com.org.mybookstore.api.util;

import org.springframework.http.HttpStatus;

public record ErrorDetails( String message, HttpStatus status) {

}
