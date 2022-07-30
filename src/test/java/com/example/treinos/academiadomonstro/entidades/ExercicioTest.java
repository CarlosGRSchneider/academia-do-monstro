package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExercicioTest {

    @Test
    void deveCriarUmExercicioCorretamenteComUmExercicioForm() {

        ExercicioForm form = new ExercicioForm("Teste", "descrição teste", "PERNA");
        Exercicio exercicio = Exercicio.novoExercicio(form);

        assertEquals(form.getNome(), exercicio.getNome());
        assertEquals(form.getDescricao(), exercicio.getDescricao());
        assertEquals(form.getGrupoMuscular(), exercicio.getGrupoMuscular().toString());
    }

}