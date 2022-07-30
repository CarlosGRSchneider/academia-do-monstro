package com.example.treinos.academiadomonstro.controllers.forms;

import com.example.treinos.academiadomonstro.commons.validators.GrupoMuscularCheck;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ExercicioForm {

    @NotBlank
    @Size(max = 40)
    private String nome;

    private String descricao;
//@GrupoMuscularCheck(anyOf = {GrupoMuscular.ABDOMEN, GrupoMuscular.BRACO, GrupoMuscular.COSTAS, GrupoMuscular.OMBRO, GrupoMuscular.PEITO, GrupoMuscular.PERNA})
    @GrupoMuscularCheck(enumClass = GrupoMuscular.class)
    private String grupoMuscular;

    public ExercicioForm(String nome, String descricao, String grupoMuscular) {
        this.nome = nome;
        this.descricao = descricao;
        this.grupoMuscular = grupoMuscular;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }
}
