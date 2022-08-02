package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoIdForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExercicioDeTreinoTest {


    private  Exercicio exercicio;

    @BeforeEach
    void setUp() {
        ExercicioForm form = new ExercicioForm("Exercicio teste", "Descrição", "PERNA");
        exercicio = Exercicio.novoExercicio(form);
    }

    @Test
    void deveMontarUmExercicioDeTreinoComOFormDeId() {
        ExercicioDeTreinoIdForm form = new ExercicioDeTreinoIdForm(1, "3 x 10", 5);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(form, exercicio);

        assertEquals(exercicio, exercicioDeTreino.getExercicio());
        assertEquals(form.getPeso(), exercicioDeTreino.getPeso());
        assertEquals(form.getRepeticao(), exercicioDeTreino.getRepeticao());
    }

    @Test
    void deveMontarUmExercicioDeTreinoComOFormDeNome() {
        ExercicioDeTreinoNomeForm form = new ExercicioDeTreinoNomeForm("Exercicio teste", "3 x 10", 5);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(form, exercicio);

        assertEquals(exercicio, exercicioDeTreino.getExercicio());
        assertEquals(form.getPeso(), exercicioDeTreino.getPeso());
        assertEquals(form.getRepeticao(), exercicioDeTreino.getRepeticao());
    }
}