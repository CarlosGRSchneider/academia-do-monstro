package com.example.treinos.academiadomonstro.controllers.dtos;

import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;

public class ExercicioDto {

    private String nome;
    private String descricao;

    private GrupoMuscular grupoMuscular;

    public ExercicioDto(Exercicio exercicio) {
        this.nome = exercicio.getNome();
        this.descricao = exercicio.getDescricao();
        this.grupoMuscular = exercicio.getGrupoMuscular();
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
