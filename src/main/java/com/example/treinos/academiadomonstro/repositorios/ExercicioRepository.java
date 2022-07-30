package com.example.treinos.academiadomonstro.repositorios;

import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExercicioRepository extends JpaRepository<Exercicio, Integer> {

    List<Exercicio> findAllByGrupoMuscular(GrupoMuscular nomeGrupo);

    Optional<Exercicio> findByNome(String nomeExercicio);
}
