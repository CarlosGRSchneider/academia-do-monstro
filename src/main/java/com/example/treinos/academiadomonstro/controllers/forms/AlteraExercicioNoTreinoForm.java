package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.entidades.Treino;

import javax.validation.constraints.NotNull;

public class AlteraExercicioNoTreinoForm {

    @NotNull
    @FieldFinder(domainClass = Treino.class, fieldName = "id")
    private int idTreino;

    @NotNull
    @FieldFinder(domainClass = ExercicioDeTreino.class, fieldName = "id")
    private int idExercicioDeTreino;

    public AlteraExercicioNoTreinoForm(int idTreino, int idExercicioDeTreino) {
        this.idTreino = idTreino;
        this.idExercicioDeTreino = idExercicioDeTreino;
    }

    public int getIdTreino() {
        return idTreino;
    }

    public int getIdExercicioDeTreino() {
        return idExercicioDeTreino;
    }
}
