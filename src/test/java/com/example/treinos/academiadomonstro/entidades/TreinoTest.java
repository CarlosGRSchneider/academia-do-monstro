package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoIdForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreinoTest {

    private List<ExercicioDeTreino> exercicioDeTreinoList = new ArrayList();

    @BeforeEach
    void setUp() {
        ExercicioForm exercicioForm = new ExercicioForm("Exercicio teste", "Descrição", "PERNA");
        Exercicio exercicio = Exercicio.novoExercicio(exercicioForm);

        ExercicioDeTreinoIdForm idForm = new ExercicioDeTreinoIdForm(1, "2 x 20", null);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(idForm, exercicio);
        exercicioDeTreinoList.add(exercicioDeTreino);
    }

    @Test
    void deveCriarUmNovoTreino() {
        TreinoForm form = new TreinoForm("Treino teste", "Descrição", Arrays.asList(1));
        Treino treino = Treino.montaTreino(form, exercicioDeTreinoList);

        assertEquals(form.getNome(), treino.getNome());
        assertEquals(form.getDescricao(), treino.getDescricao());
        assertEquals(exercicioDeTreinoList, treino.getExercicios());
        assertTrue(treino.getSnAtivo());
    }
}