package com.example.treinos.academiadomonstro.commons.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErroPadrao {

    String campo;
    String mensagem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy 'T'HH:mm:ss", timezone = "GMT")
    LocalDateTime instante;

    public ErroPadrao(String campo, String erro, LocalDateTime instante) {
        this.campo = campo;
        this.mensagem = erro;
        this.instante = instante;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getInstante() {
        return instante;
    }
}

