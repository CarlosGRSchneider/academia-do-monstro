package com.example.treinos.academiadomonstro.repositorios;

import com.example.treinos.academiadomonstro.entidades.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Integer> {

    @Query(value = "select tr.* from treino tr\n" +
            "join diaria di on di.treino = tr.id \n" +
            "where di.data < (current_date - 15)", nativeQuery = true)
    List<Treino> findAllTreinosOciosos();
}
