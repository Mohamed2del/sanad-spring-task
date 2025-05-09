package org.example.sanadspringtask.dto;

public class GenericResponse<T> {
    private int code;
    private T result;

    public GenericResponse() {}

    public GenericResponse(int code, T result) {
        this.code = code;
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
}
