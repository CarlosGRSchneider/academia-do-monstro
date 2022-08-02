package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.Treino;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TreinoStatusForm {

    @NotNull
    @FieldFinder(domainClass = Treino.class, fieldName = "id")
    private int id;

    @NotNull
    private boolean status;

    public TreinoStatusForm(int id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }
}
