package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoIdForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;

import javax.persistence.*;

@Entity
public class ExercicioDeTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Exercicio exercicio;

    @Column(nullable = false, length = 50)
    private String repeticao;

    private Integer peso;

    @Deprecated
    public ExercicioDeTreino() {
    }

    private ExercicioDeTreino(Exercicio exercicio, String repeticao, Integer peso) {
        this.exercicio = exercicio;
        this.repeticao = repeticao;
        this.peso = peso;
    }

    public static ExercicioDeTreino montaExercicioDoTreino(ExercicioDeTreinoIdForm form, Exercicio exercicio) {
        return new ExercicioDeTreino(exercicio, form.getRepeticao(), form.getPeso());
    }

    public static ExercicioDeTreino montaExercicioDoTreino(ExercicioDeTreinoNomeForm form, Exercicio exercicio) {
        return new ExercicioDeTreino(exercicio, form.getRepeticao(), form.getPeso());
    }

    public Integer getId() {
        return id;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public Integer getPeso() {
        return peso;
    }
}
