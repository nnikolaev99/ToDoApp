package com.deikioveca.ToDoApp.controller;

import com.deikioveca.ToDoApp.entity.ToDo;
import com.deikioveca.ToDoApp.service.ToDoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest(ToDoController.class)
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ToDoServiceImpl toDoService;

    private ToDo toDo;

    @BeforeEach
    void setUp() {
        this.toDo = ToDo.builder().id(1).toDo("Keep grinding").isFinished(false).build();
    }

    @Test
    void createToDo() throws Exception {
        Mockito.when(toDoService.createToDo(toDo)).thenReturn(toDo);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/createToDo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toDo)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toDo", Matchers.is("Keep grinding")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteToDo() throws Exception {
        Mockito.when(toDoService.getToDoById(toDo.getId())).thenReturn(toDo);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteToDo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateToDo() throws Exception {
        ToDo toDo1 = new ToDo(2, "Do your chores", true);
        Mockito.when(toDoService.getToDoById(toDo.getId())).thenReturn(toDo);
        Mockito.when(toDoService.createToDo(toDo)).thenReturn(toDo);
        Mockito.when(toDoService.updateToDoById(toDo.getId(), toDo1)).thenReturn(Optional.of(toDo1));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/updateToDo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(toDo1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toDo", Matchers.is("Do your chores")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFinished", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void listOfToDos() throws Exception {
        List<ToDo> toDos = new ArrayList<>(List.of(toDo, new ToDo(2, "Dont back down", false)));
        Mockito.when(toDoService.listOfToDos()).thenReturn(toDos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/listOfToDos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].toDo", Matchers.is("Keep grinding")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].toDo", Matchers.is("Dont back down")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getToDoById() throws Exception {
        Mockito.when(toDoService.getToDoById(toDo.getId())).thenReturn(toDo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/toDo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toDo", Matchers.is("Keep grinding")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFinished", Matchers.is(false)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}