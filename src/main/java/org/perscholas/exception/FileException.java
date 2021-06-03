package org.perscholas.exception;

public class FileException extends RuntimeException{

    private static final long serialVersionUID= 1L;

    private String message;

    public FileException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
