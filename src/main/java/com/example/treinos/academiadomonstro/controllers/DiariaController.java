package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.commons.exceptions.MonstroException;
import com.example.treinos.academiadomonstro.controllers.dtos.DiariaDto;
import com.example.treinos.academiadomonstro.controllers.forms.DiariaForm;
import com.example.treinos.academiadomonstro.entidades.Diaria;
import com.example.treinos.academiadomonstro.entidades.Treino;
import com.example.treinos.academiadomonstro.repositorios.DiariaRepository;
import com.example.treinos.academiadomonstro.repositorios.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/diarias")
public class DiariaController {

    @Autowired
    private DiariaRepository repository;

    @Autowired
    private TreinoRepository treinoRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastraDiaria(@RequestBody @Valid DiariaForm form) {

        Treino treino = treinoRepository.findById(form.getId()).get();
        if (!treino.getSnAtivo()) {
            throw new MonstroException("Monstro meu rei, não seria melhor ativar o treino antes de usar ele?", "Treino", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Diaria diaria = Diaria.montaDiaria(form, treino);
        repository.save(diaria);
    }

    @GetMapping
    public ResponseEntity<List<DiariaDto>> encontraTodasAsDiarias() {

        List<Diaria> diarias = repository.findAll(Sort.by(Sort.Direction.DESC, "data"));
        List<DiariaDto> diariaDtos = diarias.stream().map(DiariaDto::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(diariaDtos);
    }
}
