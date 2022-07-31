package com.example.treinos.academiadomonstro.repositorios;

import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioDeTreinoRepository extends JpaRepository<ExercicioDeTreino, Integer> {

    List<ExercicioDeTreino> findAllByExercicio_NomeIgnoreCase(String nome);
}
