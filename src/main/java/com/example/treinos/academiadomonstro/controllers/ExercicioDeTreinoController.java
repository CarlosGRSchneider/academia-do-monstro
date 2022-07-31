package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.commons.exceptions.MonstroException;
import com.example.treinos.academiadomonstro.controllers.dtos.ExercicioDeTreinoDto;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoIdForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.repositorios.ExercicioDeTreinoRepository;
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
@RequestMapping("/exercicio-de-treino")
public class ExercicioDeTreinoController {

    @Autowired
    private ExercicioDeTreinoRepository repository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @PostMapping("/id")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastraExercicioDeTreino(@RequestBody @Valid ExercicioDeTreinoIdForm form) {
        Exercicio exercicio = exercicioRepository.findById(form.getIdExercicio()).get();

        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(form, exercicio);
        repository.save(exercicioDeTreino);
    }

    @PostMapping("/nome")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastraExercicioDeTreino(@RequestBody @Valid ExercicioDeTreinoNomeForm form) {
        Exercicio exercicio = exercicioRepository.findByNome(form.getNomeExercicio()).get();

        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(form, exercicio);
        repository.save(exercicioDeTreino);

    }

    @GetMapping
    public ResponseEntity<List<ExercicioDeTreinoDto>> getAllExerciciosDeTreino() {
        List<ExercicioDeTreino> exerciciosDeTreino = repository.findAll();
        List<ExercicioDeTreinoDto> exercicioDtos = exerciciosDeTreino.stream().map(ExercicioDeTreinoDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(exercicioDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExercicioDeTreinoDto> getExercicioDeTreinoPorId(@PathVariable int id) {
        Optional<ExercicioDeTreino> exercicio = repository.findById(id);
        if (exercicio.isPresent()) {
            return ResponseEntity.ok().body(new ExercicioDeTreinoDto(exercicio.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{nome}/nome-exercicio")
    public ResponseEntity<List<ExercicioDeTreinoDto>> getAllExerciciosDeTreinoPorNomeDoExercicio(@PathVariable String nome) {

        Optional<Exercicio> possivelExercicio = exercicioRepository.findByNomeIgnoreCase(nome);
        if (!possivelExercicio.isPresent()) {
            throw new MonstroException("Meu monstro, não tem nenhum exercicio chamado " + nome + " na base de dados", "Nome exercicio", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        List<ExercicioDeTreino> exerciciosDeTreino = repository.findAllByExercicio_NomeIgnoreCase(nome);
        if (exerciciosDeTreino.size() > 0) {
            List<ExercicioDeTreinoDto> exercicioDtos = exerciciosDeTreino.stream().map(ExercicioDeTreinoDto::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(exercicioDtos);
        }
        return ResponseEntity.noContent().build();
    }
}
