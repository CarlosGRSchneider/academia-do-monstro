package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.FieldFinder;
import com.example.treinos.academiadomonstro.entidades.Treino;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class DiariaForm {

    @NotNull
    @FieldFinder(fieldName = "id", domainClass = Treino.class)
    private int id;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate data;

    public DiariaForm(int id, LocalDate data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }
}
