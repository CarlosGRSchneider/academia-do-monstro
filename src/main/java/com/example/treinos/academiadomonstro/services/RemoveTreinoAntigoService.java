package com.example.treinos.academiadomonstro.services;

import com.example.treinos.academiadomonstro.entidades.Treino;
import com.example.treinos.academiadomonstro.repositorios.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RemoveTreinoAntigoService {

    @Autowired
    private TreinoRepository treinoRepository;

    @Scheduled(fixedDelayString = "1", timeUnit = TimeUnit.DAYS)
    private void removeTreinosAntigos() {

        List<Treino> treinosAntigos = treinoRepository.findAllTreinosOciosos();

        if(treinosAntigos.isEmpty()) {
            return;
        }

        treinosAntigos.stream().forEach(treino -> treino.setSnAtivo(false));

        treinoRepository.saveAll(treinosAntigos);
    }
}
