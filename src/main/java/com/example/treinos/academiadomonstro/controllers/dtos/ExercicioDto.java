package com.example.treinos.academiadomonstro.controllers.dtos;

import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;

public class ExercicioDto {

    private int id;
    private String nome;
    private String descricao;

    private GrupoMuscular grupoMuscular;

    public ExercicioDto(Exercicio exercicio) {
        this.id = exercicio.getId();
        this.nome = exercicio.getNome();
        this.descricao = exercicio.getDescricao();
        this.grupoMuscular = exercicio.getGrupoMuscular();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }
}
