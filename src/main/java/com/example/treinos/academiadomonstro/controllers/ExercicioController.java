package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.commons.exceptions.MonstroException;
import com.example.treinos.academiadomonstro.controllers.dtos.ExercicioDto;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.enums.GrupoMuscular;
import com.example.treinos.academiadomonstro.repositorios.ExercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insereExercicio(@RequestBody @Valid ExercicioForm exercicioForm) {

        Exercicio exercicio = Exercicio.novoExercicio(exercicioForm);
        exercicioRepository.save(exercicio);

    }

    @GetMapping
    public ResponseEntity<List<ExercicioDto>> getAllExercicios() {
        List<Exercicio> exercicios = exercicioRepository.findAll();
        List<ExercicioDto> exercicioDtos = exercicios.stream().map(ExercicioDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(exercicioDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExercicioDto> getExercicioPorId(@PathVariable int id) {

        Optional<Exercicio> exercicio = exercicioRepository.findById(id);
        if (exercicio.isPresent()) {
            return ResponseEntity.ok().body(new ExercicioDto(exercicio.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{grupo}/grupo")
    public ResponseEntity<List<ExercicioDto>> getAllExerciciosPorGrupoMuscular(@PathVariable String grupo) {

        GrupoMuscular grupoMuscular;
        try {
            grupoMuscular = GrupoMuscular.valueOf(grupo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MonstroException("O grupo muscular " + grupo + " informado aí não existe meu monstro", "Grupo muscular", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<Exercicio> exercicios = exercicioRepository.findAllByGrupoMuscular(grupoMuscular);

        if (exercicios.size() > 0) {
            List<ExercicioDto> exercicioDtos = exercicios.stream().map(ExercicioDto::new).collect(Collectors.toList());
            return ResponseEntity.ok().body(exercicioDtos);
        }
        return ResponseEntity.noContent().build();
    }
}
