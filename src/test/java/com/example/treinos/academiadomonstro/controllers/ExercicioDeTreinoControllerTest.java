package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioDeTreinoNomeForm;
import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
import com.example.treinos.academiadomonstro.entidades.ExercicioDeTreino;
import com.example.treinos.academiadomonstro.repositorios.ExercicioDeTreinoRepository;
import com.example.treinos.academiadomonstro.repositorios.ExercicioRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class ExercicioDeTreinoControllerTest {

    private String uri = "/exercicio-de-treino";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExercicioDeTreinoRepository repository;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @BeforeEach
    void setUp() {

        ExercicioForm form = new ExercicioForm("Abdominal", "Abdominal comum", "Abdomen");
        Exercicio exercicio = Exercicio.novoExercicio(form);
        ExercicioForm form2 = new ExercicioForm("Voador", "Exercicio de peito com maquina", "Peito");
        Exercicio exercicio2 = Exercicio.novoExercicio(form2);
        exercicioRepository.saveAll(Arrays.asList(exercicio, exercicio2));

        ExercicioDeTreinoNomeForm treinoNomeForm = new ExercicioDeTreinoNomeForm("Abdominal", "3 x 10", null);
        ExercicioDeTreino exercicioDeTreino = ExercicioDeTreino.montaExercicioDoTreino(treinoNomeForm, exercicio);
        repository.save(exercicioDeTreino);
    }

    @Test
    void deveCadastrarUmNovoExercicioDeTreinoComOIdDoExercicio() throws Exception {

        List<ExercicioDeTreino> listaDeTreino = repository.findAll();
        assertEquals(1, listaDeTreino.size());

        String form = "{\"idExercicio\" : 1, \"repeticao\" : \"5 x 20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/id")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        listaDeTreino = repository.findAll();
        assertEquals(2, listaDeTreino.size());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComIdSemRepeticao() throws Exception {


        String form = "{\"idExercicio\" : 1, \"peso\" : 5}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/id")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComIdComRepeticaoMuitoLonga() throws Exception {

        String form = "{\"idExercicio\" : 1, \"repeticao\" : \"5 series de repeticoes contendo 20 exercicios em cada serie realizada\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/id")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComOIdDoExercicioEPesoNegativo() throws Exception {

        String form = "{\"idExercicio\" : 1, \"repeticao\" : \"5 x 20\", \"peso\" : -3}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/id")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deveCadastrarUmNovoExercicioDeTreinoComONomeDoExercicio() throws Exception {

        List<ExercicioDeTreino> listaDeTreino = repository.findAll();
        assertEquals(1, listaDeTreino.size());

        String form = "{\"nomeExercicio\" : \"Abdominal\", \"repeticao\" : \"5 x 20\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/nome")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        listaDeTreino = repository.findAll();
        assertEquals(2, listaDeTreino.size());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComNomeSemRepeticao() throws Exception {


        String form = "{\"nomeExercicio\" : \"Abdominal\", \"peso\" : 5}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/nome")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComNomeComRepeticaoMuitoLonga() throws Exception {

        String form = "{\"nomeExercicio\" : \"Abdominal\",, \"repeticao\" : \"5 series de repeticoes contendo 20 exercicios em cada serie realizada\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/nome")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveCadastrarUmNovoExercicioDeTreinoComONomeDoExercicioEPesoNegativo() throws Exception {

        String form = "{\"nomeExercicio\" : \"Abdominal\", \"repeticao\" : \"5 x 20\", \"peso\" : -3}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri + "/nome")
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deveEncontrarAListaDeExerciciosDeTreinoCorretaComOGetAll() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"nomeExercicio\":\"Abdominal\",\"repeticao\":\"3 x 10\",\"peso\":null}]"));
    }

    @Test
    void deveLancarErro404SeOIdInformadoNaoExistirComOGetPorId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void deveEncontrarUmExercicioDeTreinoAoInformarOIdCorretoComOGetPorId() throws Exception {

       mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'id':1,'nomeExercicio':'Abdominal','repeticao':'3 x 10','peso':null}"));
    }

    @Test
    void deveEncontrarAListaDeExercicioDeTreinoAoInformarONomeCorretoComOGetPorNome() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/abdominal/nome-exercicio"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'id':1,'nomeExercicio':'Abdominal','repeticao':'3 x 10','peso':null}]"));
    }

    @Test
    void naoDeveEncontrarAListaDeExercicioDeTreinoAoInformarONomeDeUmExercicioInexistenteComOGetPorNome() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/flexao/nome-exercicio"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void naoDeveEncontrarAListaDeExercicioDeTreinoAoInformarUmNomeExistenteMasQueNaoTemExercicioDeTreinoComOGetPorNome() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/voador/nome-exercicio"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}