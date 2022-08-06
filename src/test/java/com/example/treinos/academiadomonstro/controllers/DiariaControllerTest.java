package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.controllers.forms.DiariaForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;
import com.example.treinos.academiadomonstro.entidades.Diaria;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.entidades.Treino;
import com.example.treinos.academiadomonstro.repositorios.DiariaRepository;
import com.example.treinos.academiadomonstro.repositorios.ExercicioDeTreinoRepository;
import com.example.treinos.academiadomonstro.repositorios.ExercicioRepository;
import com.example.treinos.academiadomonstro.repositorios.TreinoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class DiariaControllerTest {

    private String uri = "/diarias";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiariaRepository repository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ExercicioDeTreinoRepository exercicioDeTreinoRepository;

    @Autowired
    private TreinoRepository treinoRepository;

    @BeforeEach
    void setUp() {
        ExercicioForm form = new ExercicioForm("Abdominal", "Abdominal comum", "Abdomen");
        Exercicio exercicio = Exercicio.novoExercicio(form);
        ExercicioForm form2 = new ExercicioForm("Voador", "Exercicio de peito com maquina", "Peito");
        Exercicio exercicio2 = Exercicio.novoExercicio(form2);
        exercicioRepository.saveAll(Arrays.asList(exercicio, exercicio2));

        ExercicioDeTreinoNomeForm treinoNomeForm = new ExercicioDeTreinoNomeForm("Abdominal", "3 x 10", null);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(treinoNomeForm, exercicio);
        ExercicioDeTreinoNomeForm treinoNomeForm2 = new ExercicioDeTreinoNomeForm("Voador", "4 x 15", 30);
        ExercicioDeTreino exercicioDeTreino2 = ExercicioDeTreino.montaExercicioDoTreino(treinoNomeForm2, exercicio2);
        exercicioDeTreinoRepository.saveAll(Arrays.asList(exercicioDeTreino, exercicioDeTreino2));

        TreinoForm treinoForm1 = new TreinoForm("Treino A", null, Arrays.asList(1, 2));
        Treino treino1 = Treino.montaTreino(treinoForm1, Arrays.asList(exercicioDeTreino, exercicioDeTreino2));
        TreinoForm treinoForm2 = new TreinoForm("Treino B", null, Arrays.asList(1));
        Treino treino2 = Treino.montaTreino(treinoForm2, Arrays.asList(exercicioDeTreino));
        treino2.setSnAtivo(false);
        treinoRepository.saveAll(Arrays.asList(treino1, treino2));

        DiariaForm diariaForm = new DiariaForm(1, LocalDate.of(2020, 10, 13));
        Diaria diaria = Diaria.montaDiaria(diariaForm, treino1);
        repository.save(diaria);
    }

    @Test
    void deveRegistrarUmaNovaDiaria() throws Exception {

        List<Diaria> diarias = repository.findAll();
        assertEquals(1, diarias.size());

        String form = "{\"id\" : 1}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        diarias = repository.findAll();
        assertEquals(2, diarias.size());
    }

    @Test
    void naoDeveRegistrarUmaNovaDiariaComIdDeTreinoInvalido() throws Exception {

        String form = "{\"id\" : 10}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveRegistrarUmaNovaDiariaSemId() throws Exception {

        String form = "{\"data\" : \"10/02/2020\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveRegistrarUmaNovaDiariaComDataNoFuturo() throws Exception {

        LocalDate hoje = LocalDate.now().plusDays(2);

        String form = "{\"id\" : \"data\" : \"" + hoje + "\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmaDiariaComUmTreinoQueEstaInativo() throws Exception {

        LocalDate hoje = LocalDate.now();

        String form = "{\"id\" : 2, \"data\" : \"" + hoje + "\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void deveRetornarAListaDeDiarias() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'id':1,'treino':'Treino A','data':'13/10/2020'}]"));
    }
}