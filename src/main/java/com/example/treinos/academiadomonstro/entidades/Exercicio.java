package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;

import javax.persistence.*;

@Entity
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 40)
    private String nome;
    private String descricao;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GrupoMuscular grupoMuscular;

    @Deprecated
    public Exercicio() {
    }

    private Exercicio(String nome, String descricao, GrupoMuscular grupoMuscular) {
        this.nome = nome;
        this.descricao = descricao;
        this.grupoMuscular = grupoMuscular;
    }

    public static Exercicio novoExercicio(ExercicioForm form) {
        return new Exercicio(form.getNome(), form.getDescricao(), GrupoMuscular.valueOf(form.getGrupoMuscular().toUpperCase()));
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }
}
