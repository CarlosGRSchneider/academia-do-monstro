package com.example.treinos.academiadomonstro.controllers.dtos;

import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;

public class ExercicioDeTreinoDto {

    private int id;
    private String nomeExercicio;
    private String repeticao;
    private Integer peso;

    public ExercicioDeTreinoDto(ExercicioDeTreino exercicioDeTreino) {
        this.id = exercicioDeTreino.getId();
        this.nomeExercicio = exercicioDeTreino.getExercicio().getNome();
        this.repeticao = exercicioDeTreino.getRepeticao();
        this.peso = exercicioDeTreino.getPeso();
    }

    public int getId() {
        return id;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public Integer getPeso() {
        return peso;
    }
}
