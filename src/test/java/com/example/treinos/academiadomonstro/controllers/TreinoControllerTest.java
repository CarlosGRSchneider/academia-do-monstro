package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.controllers.forms.TreinoForm;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.entidades.Treino;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class TreinoControllerTest {

    private String uri = "/treinos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private ExercicioDeTreinoRepository exercicioDeTreinoRepository;

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

        TreinoForm treinoForm = new TreinoForm("Treino A", null, Arrays.asList(1, 2));
        Treino treino = Treino.montaTreino(treinoForm, Arrays.asList(exercicioDeTreino, exercicioDeTreino2));
        treinoRepository.save(treino);
    }

    @Test
    void deveCadastrarUmNovoTreino() throws Exception {

        List<Treino> treinos = treinoRepository.findAll();
        assertEquals(1, treinos.size());

        String form = "{\"nome\" : \"novo treino\", \"descricao\" : \"descricao novo treino\", \"idsDeExercicio\" : [2]}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        treinos = treinoRepository.findAll();
        assertEquals(2, treinos.size());
    }

    @Test
    void naoDeveCadastrarUmNovoTreinoComNomeEmBranco() throws Exception {

        String form = "{\"nome\" : \"\", \"descricao\" : \"descricao novo treino\", \"idsDeExercicio\" : [2]}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoTreinoComNomeMuitoLongo() throws Exception {

        String form = "{\"nome\" : \"novo treino, porem o nome dele e muito extenso e a validaçao acaba falhando\", \"descricao\" : \"descricao novo treino\", \"idsDeExercicio\" : [2]}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoTreinoComIdDeExercicioInexistente() throws Exception {

        String form = "{\"nome\" : \"mais um treino\", \"descricao\" : \"descricao novo treino\", \"idsDeExercicio\" : [20]}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deveEncontrarAListaDeTreinosComOGetAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"nome\":\"Treino A\",\"descricao\":null,\"exerciciosDoTreino\":[{\"id\":1,\"nomeExercicio\":\"Abdominal\",\"repeticao\":\"3 x 10\",\"peso\":null},{\"id\":2,\"nomeExercicio\":\"Voador\",\"repeticao\":\"4 x 15\",\"peso\":30}],\"status\":\"Ativo\"}]"));
    }

    @Test
    void deveLancarErro404AoInformarUmIdDeTreinoInexistente() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deveEncontrarUmTreinoComOGetPorId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"nome\":\"Treino A\",\"descricao\":null,\"exerciciosDoTreino\":[{\"id\":1,\"nomeExercicio\":\"Abdominal\",\"repeticao\":\"3 x 10\",\"peso\":null},{\"id\":2,\"nomeExercicio\":\"Voador\",\"repeticao\":\"4 x 15\",\"peso\":30}],\"status\":\"Ativo\"}"));
    }


//    @Test
//    void deveAlterarOStatusDeUmTreinoComOAlteraStatusTreino() throws Exception {
//
//        String form = "{\"id\" : 1, \"status\" : false}";
//
//        mockMvc.perform(MockMvcRequestBuilders.patch(uri + "/status")
//                .content(form)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

}