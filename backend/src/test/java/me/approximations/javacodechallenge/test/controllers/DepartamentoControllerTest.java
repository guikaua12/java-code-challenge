package me.approximations.javacodechallenge.test.controllers;

import me.approximations.javacodechallenge.entities.Departamento;
import me.approximations.javacodechallenge.repositories.DepartamentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DepartamentoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartamentoRepository departamentoRepository;

    @Test
    public void shouldGetAllDepartments() throws Exception {
        Departamento dep1 = new Departamento(1L, "Department 1");
        Departamento dep2 = new Departamento(2L, "Department 2");

        Page<Departamento> page = new PageImpl<>(Arrays.asList(dep1, dep2));

        when(departamentoRepository.getAll(PageRequest.of(0, 20))).thenReturn(page);

        mockMvc.perform(get("/department/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)));
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {
        Departamento dep = new Departamento(1L, "Department 1");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(dep));

        mockMvc.perform(get("/department/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void shouldNotFindDepartmentById() throws Exception {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/department/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
