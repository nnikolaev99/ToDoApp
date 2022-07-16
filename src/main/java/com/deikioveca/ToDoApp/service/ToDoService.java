package com.deikioveca.ToDoApp.service;

import com.deikioveca.ToDoApp.entity.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoService {

    ToDo createToDo(ToDo toDo);

    void deleteToDoById(Integer id) throws Exception;

    Optional<ToDo> updateToDoById(Integer id, ToDo toDo) throws Exception;

    List<ToDo> listOfToDos();

    ToDo getToDoById(Integer id) throws Exception;
}
