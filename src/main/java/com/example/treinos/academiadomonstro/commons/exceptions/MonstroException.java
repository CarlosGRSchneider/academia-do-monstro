package com.example.treinos.academiadomonstro.commons.exceptions;


import org.springframework.http.HttpStatus;

public class MonstroException extends RuntimeException {

    private String mensagem;
    private String campo;
    private HttpStatus httpStatus;

    public MonstroException(String mensagem, String campo, HttpStatus httpStatus) {
        super(mensagem);
        this.campo = campo;
        this.httpStatus = httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
