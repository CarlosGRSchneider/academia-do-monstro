package com.example.treinos.academiadomonstro.controllers.dtos;

import com.example.treinos.academiadomonstro.entidades.Diaria;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DiariaDto {

    private int id;
    private String treino;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    public DiariaDto(Diaria diaria) {
        this.id = diaria.getId();
        this.treino = diaria.getTreino().getNome();
        this.data = diaria.getData();
    }

    public int getId() {
        return id;
    }

    public String getTreino() {
        return treino;
    }

    public LocalDate getData() {
        return data;
    }
}
