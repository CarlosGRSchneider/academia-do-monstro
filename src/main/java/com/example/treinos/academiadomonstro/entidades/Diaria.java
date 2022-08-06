package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.DiariaForm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Diaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Treino treino;

    @NotNull
    private LocalDate data;

    @Deprecated
    public Diaria() {

    }

    private Diaria(Treino treino, LocalDate data) {

        if (Objects.isNull(data)) {
            data = LocalDate.now();
        }

        this.treino = treino;
        this.data = data;
    }

    public static Diaria montaDiaria(DiariaForm form, Treino treino) {
        return new Diaria(treino, form.getData());
    }

    public Integer getId() {
        return id;
    }

    public Treino getTreino() {
        return treino;
    }

    public LocalDate getData() {
        return data;
    }
}
