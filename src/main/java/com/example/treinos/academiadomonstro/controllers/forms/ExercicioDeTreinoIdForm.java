package com.example.treinos.academiadomonstro.controllers.forms;


import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.Exercicio;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ExercicioDeTreinoIdForm {

    @NotNull
    @FieldFinder(domainClass = Exercicio.class, fieldName = "id")
    private Integer idExercicio;

    @NotBlank
    @Size(max = 50)
    private String repeticao;

    @Positive
    private Integer peso;

    public ExercicioDeTreinoIdForm(Integer idExercicio, String repeticao, Integer peso) {
        this.idExercicio = idExercicio;
        this.repeticao = repeticao;
        this.peso = peso;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public Integer getPeso() {
        return peso;
    }
}