package com.example.treinos.academiadomonstro.entidades;

import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    private String descricao;

    private Boolean snAtivo;

    @OneToMany
    private List<ExercicioDeTreino> exercicios = new ArrayList<>();

    @Deprecated
    public Treino() {
    }

    private Treino(String nome, String descricao, List<ExercicioDeTreino> exercicios) {
        this.nome = nome;
        this.descricao = descricao;
        this.exercicios = exercicios;
        this.snAtivo = true;
    }

    public static Treino montaTreino(TreinoForm form, List<ExercicioDeTreino> exercicioDeTreinoList) {
        return new Treino(form.getNome(), form.getDescricao(), exercicioDeTreinoList);
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<ExercicioDeTreino> getExercicios() {
        return exercicios;
    }

    public Boolean getSnAtivo() {
        return snAtivo;
    }

    public void setSnAtivo(Boolean snAtivo) {
        this.snAtivo = snAtivo;
    }
}
