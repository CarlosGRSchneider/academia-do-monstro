package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.commons.exceptions.MonstroException;
import com.example.treinos.academiadomonstro.controllers.dtos.TreinoDto;
import com.example.treinos.academiadomonstro.controllers.forms.AlteraExercicioNoTreinoForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoStatusForm;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.entidades.Treino;
import com.example.treinos.academiadomonstro.repositorios.ExercicioDeTreinoRepository;
import com.example.treinos.academiadomonstro.repositorios.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/treinos")
public class TreinoController {


    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioDeTreinoRepository exercicioDeTreinoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionaTreino(@RequestBody @Valid TreinoForm form) {

        List<ExercicioDeTreino> exercicioDeTreinoList = form.getIdsDeExercicio().stream()
                .map(id -> exercicioDeTreinoRepository.findById(id).get())
                .collect(Collectors.toList());

        Treino treino = Treino.montaTreino(form, exercicioDeTreinoList);
        treinoRepository.save(treino);
    }

    @GetMapping
    public ResponseEntity<List<TreinoDto>> getAllTreinos() {

        List<Treino> treinos = treinoRepository.findAll();

        List<TreinoDto> treinoDtos = treinos.stream().map(TreinoDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(treinoDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinoDto> getTreinoPorId(@PathVariable int id) {

        Optional<Treino> treino = treinoRepository.findById(id);

        if (treino.isPresent()) {

            TreinoDto treinoDto = new TreinoDto(treino.get());
            return ResponseEntity.ok().body(treinoDto);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/status")
    public ResponseEntity<TreinoDto> alteraStatusTreino(@RequestBody @Valid TreinoStatusForm form) {

        Treino treino = treinoRepository.findById(form.getId()).get();

        if (form.isStatus() == treino.getSnAtivo()) {
            String msg1 = form.isStatus() ? "ativar" : "desativar";
            String msg2 = form.isStatus() ? "ativo" : "desativado";

            throw new MonstroException("Caro monstro, porque você quer " + msg1 + " um treino que já está " + msg2 + "?", "Status", HttpStatus.BAD_REQUEST);
        }
        treino.setSnAtivo(form.isStatus());
        treinoRepository.save(treino);

        return ResponseEntity.ok().body(new TreinoDto(treino));
    }

    @PutMapping("/exercicio/adiciona")
    public ResponseEntity<TreinoDto> adicionaExercicioNoTreino( @RequestBody @Valid AlteraExercicioNoTreinoForm form) {

        ExercicioDeTreino exercicioDeTreino = exercicioDeTreinoRepository.findById(form.getIdExercicioDeTreino()).get();

        Treino treino = treinoRepository.findById(form.getIdTreino()).get();

        if(treino.getExercicios().contains(exercicioDeTreino)) {
            throw new MonstroException("Meu monstro rei, porque você quer adicionar um exercicio que ja existe no treino?", "exercicios", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        treino.adicionaExercicioNoTreino(exercicioDeTreino);
        treinoRepository.save(treino);

        return ResponseEntity.ok().body(new TreinoDto(treino));
    }

    @PutMapping("/exercicio/remove")
    public ResponseEntity<TreinoDto> removeExercicioNoTreino( @RequestBody @Valid AlteraExercicioNoTreinoForm form) {

        ExercicioDeTreino exercicioDeTreino = exercicioDeTreinoRepository.findById(form.getIdExercicioDeTreino()).get();

        Treino treino = treinoRepository.findById(form.getIdTreino()).get();

        if(!treino.getExercicios().contains(exercicioDeTreino)) {
            throw new MonstroException("Meu monstro rei, porque você quer tirar um exercicio que não tem no treino?", "exercicios", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        treino.removeExercicioNoTreino(exercicioDeTreino);
        treinoRepository.save(treino);

        return ResponseEntity.ok().body(new TreinoDto(treino));
    }
}
