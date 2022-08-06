package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.DiariaForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoIdForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DiariaTest {


    private Treino treino;

    @BeforeEach
    void setUp() {

        ExercicioForm exercicioForm = new ExercicioForm("Exercicio teste", "Descrição", "PERNA");
        Exercicio exercicio = Exercicio.novoExercicio(exercicioForm);

        ExercicioDeTreinoIdForm idForm = new ExercicioDeTreinoIdForm(1, "2 x 20", null);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(idForm, exercicio);

        TreinoForm form = new TreinoForm("Treino teste", "Descricao teste", Arrays.asList(1));
        treino = Treino.montaTreino(form, Arrays.asList(exercicioDeTreino));
    }

    @Test
    void deveMontarUmaDiaria() {
        DiariaForm form = new DiariaForm(1, LocalDate.now());
        Diaria diaria = Diaria.montaDiaria(form, treino);

        assertEquals(treino, diaria.getTreino());
        assertEquals(form.getData(), diaria.getData());
    }
}