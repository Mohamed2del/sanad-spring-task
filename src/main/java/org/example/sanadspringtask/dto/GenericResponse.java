package org.example.sanadspringtask.dto;

public class GenericResponse<T> {
    private int code;
    private T result;
    private String message;

    public GenericResponse() {}

    public GenericResponse(int code, T result) {
        this.code = code;
        this.result = result;
    }

    public GenericResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public T getResult() {
        return result;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
