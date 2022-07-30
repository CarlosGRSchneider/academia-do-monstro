package com.example.treinos.academiadomonstro.controllers;

import com.example.treinos.academiadomonstro.controllers.forms.ExercicioForm;
import com.example.treinos.academiadomonstro.entidades.Exercicio;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class ExercicioControllerTest {

    private String uri = "/exercicios";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExercicioRepository exercicioRepository;

    @BeforeEach
    void setUp() {

       ExercicioForm form = new ExercicioForm("Exercicio a priori", null, "OMBRO");

        Exercicio exercicio = Exercicio.novoExercicio(form);
        exercicioRepository.save(exercicio);
    }

    @Test
    void deveInserirUmExercicioQuandoEstaTudoCorreto() throws Exception {

        List<Exercicio> exercicioList = exercicioRepository.findAll();
        assertEquals(1, exercicioList.size());
        String form = "{\"nome\" : \"teste\", \"grupoMuscular\" : \"PERNA\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        exercicioList = exercicioRepository.findAll();
        assertEquals(2, exercicioList.size());
    }

    @Test
    void naoDeveInserirUmExercicioQuandoNaoHouverNome() throws Exception {

        String form = "{\"descricao\" : \"uma descricao qualquer\", \"grupoMuscular\" : \"PERNA\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveInserirUmExercicioComNomeMaiorDe40Caracteres() throws Exception {

        String form = "{\"nome\" : \"nome comprido, que possui mais de 40 caracteres e não é comportado no banco de dados\", \"grupoMuscular\" : \"PERNA\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveInserirUmExercicioQuandoNaoHouverGrupoMuscular() throws Exception {

        String form = "{\"nome\" : \"nome\", \"descricao\" : \"uma descricao\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void naoDeveInserirUmExercicioQuandoGrupoMuscularForInvalido() throws Exception {

        String form = "{\"nome\" : \"nome\", \"descricao\" : \"uma descricao\", \"grupoMuscular\", \"CAVALO\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(form)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deveLancarErro404SeOIdInformadoNaoExistirComOGetPorId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void deveEncontrarUmObjetoAoInformarOIdCorretoComOGetPorId() throws Exception {

//        porque ele encontra ao executar individualmente, mas não ao executar na bateria?
//        ExercicioForm form = new ExercicioForm("Exercicio informado", null, "PERNA");
//        Exercicio exercicio = Exercicio.novoExercicio(form);
//        exercicioRepository.save(exercicio);
//        .andExpect(MockMvcResultMatchers.content().json("{'nome':'Exercicio informado','descricao':null,'grupoMuscular':'PERNA'}"));

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{'nome':'Exercicio a priori','descricao':null,'grupoMuscular':'OMBRO'}"));
    }

    @Test
    void deveEncontrarAListaDeExerciciosCorretaComOGetAll() throws Exception {

     mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'nome':'Exercicio a priori','descricao':null,'grupoMuscular':'OMBRO'}]"));
    }

    @Test
    void deveEncontrarAListaDeExerciciosCOrretaComOGetByGrupoMuscular() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/ombro/grupo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'nome':'Exercicio a priori','descricao':null,'grupoMuscular':'OMBRO'}]"));
    }

    @Test
    void deveLancarUmaExcecaoAoInformarUmGrupoMuscularNaoExistenteComOGetByGrupoMuscular() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/cavalo/grupo"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void deveRetornarUmNoCOntentAoEncontrarUmaListaDeExerciciosVazia() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "/abdomen/grupo"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}