package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class TreinoForm {

    @NotBlank
    @Size(max = 50)
    private String nome;

    private String descricao;

    @NotEmpty
    private List<@FieldFinder(domainClass = ExercicioDeTreino.class, fieldName = "id") Integer> idsDeExercicio = new ArrayList<>();

    public TreinoForm(String nome, String descricao, List<@FieldFinder(domainClass = ExercicioDeTreino.class, fieldName = "id") Integer> idsDeExercicio) {
        this.nome = nome;
        this.descricao = descricao;
        this.idsDeExercicio = idsDeExercicio;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Integer> getIdsDeExercicio() {
        return idsDeExercicio;
    }
}
