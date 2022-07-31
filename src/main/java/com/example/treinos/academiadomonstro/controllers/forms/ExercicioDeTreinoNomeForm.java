package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.Exercicio;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ExercicioDeTreinoNomeForm {

    @NotNull
    @FieldFinder(domainClass = Exercicio.class, fieldName = "nome")
    private String nomeExercicio;

    @NotBlank
    @Size(max = 50)
    private String repeticao;

    @Positive
    private Integer peso;

    public ExercicioDeTreinoNomeForm(String nomeExercicio, String repeticao, Integer peso) {
        this.nomeExercicio = nomeExercicio;
        this.repeticao = repeticao;
        this.peso = peso;
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
