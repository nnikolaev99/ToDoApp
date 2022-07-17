package com.deikioveca.ToDoApp.service;

import com.deikioveca.ToDoApp.entity.ToDo;
import com.deikioveca.ToDoApp.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ToDoServiceImplTest {

    private final ToDoServiceImpl toDoService;

    @Autowired
    public ToDoServiceImplTest(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @MockBean
    private ToDoRepository toDoRepository;

    private ToDo toDo;

    @BeforeEach
    void setUp() {
        this.toDo = ToDo.builder().id(1).toDo("Do your chores").isFinished(false).build();
    }

    @Test
    void createToDo() {
        Mockito.when(toDoRepository.save(Mockito.any(ToDo.class))).thenReturn(toDo);
        ToDo toDo1 = toDoService.createToDo(toDo);
        assertNotNull(toDo1);
        assertEquals(toDo.getToDo(), toDo1.getToDo());
    }

    @Test
    void deleteToDoById() throws Exception {
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        Mockito.doNothing().when(toDoRepository).deleteById(toDo.getId());
        toDoService.deleteToDoById(toDo.getId());
        Mockito.verify(toDoRepository, Mockito.times(1)).deleteById(toDo.getId());
    }

    @Test
    void updateToDoById() throws Exception {
        Mockito.when(toDoRepository.save(toDo)).thenReturn(toDo);
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        Optional<ToDo> toDo1 = toDoService.updateToDoById(toDo.getId(), new ToDo(2, "d", true));
        assertNotNull(toDo1);
        assertEquals(toDo1.get().getToDo(), toDo.getToDo());
    }

    @Test
    void listOfToDos() {
        ToDo secondToDo = new ToDo(2, "Never stop grinding", false);
        Mockito.when(toDoRepository.findAll()).thenReturn(List.of(toDo, secondToDo));
        List<ToDo> toDos = toDoService.listOfToDos();
        assertEquals(toDos.size(), 2);
    }

    @Test
    void getToDoById() throws Exception {
        Mockito.when(toDoRepository.findById(toDo.getId())).thenReturn(Optional.of(toDo));
        ToDo toDo1 = toDoService.getToDoById(toDo.getId());
        assertEquals(toDo1.getToDo(), toDo.getToDo());
    }
}