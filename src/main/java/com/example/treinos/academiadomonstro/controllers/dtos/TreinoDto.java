package com.example.treinos.academiadomonstro.controllers.dtos;

import com.example.treinos.academiadomonstro.entidades.Treino;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreinoDto {

    private Integer id;
    private String nome;
    private String descricao;
    private List<ExercicioDeTreinoDto> exerciciosDoTreino = new ArrayList<>();

    private String status;

    public TreinoDto(Treino treino) {
        this.id = treino.getId();
        this.nome = treino.getNome();
        this.descricao = treino.getDescricao();
        this.exerciciosDoTreino = treino.getExercicios().stream()
                .map(ExercicioDeTreinoDto::new).collect(Collectors.toList());
        this.status = treino.getSnAtivo() ? "Ativo" : "Desativado";
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<ExercicioDeTreinoDto> getExerciciosDoTreino() {
        return exerciciosDoTreino;
    }

    public String getStatus() {
        return status;
    }
}
