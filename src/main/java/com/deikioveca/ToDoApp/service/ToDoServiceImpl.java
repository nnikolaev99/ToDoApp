package com.deikioveca.ToDoApp.service;

import com.deikioveca.ToDoApp.entity.ToDo;
import com.deikioveca.ToDoApp.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    @Override
    public void deleteToDoById(Integer id) throws Exception {
        Optional<ToDo> toDo = toDoRepository.findById(id);
        if (!toDo.isPresent()) {
            throw new Exception(String.format("ToDo with id: %d does not exist!", id));
        }
        toDoRepository.delete(toDo.get());
    }

    @Override
    public Optional<ToDo> updateToDoById(Integer id, ToDo toDo) throws Exception {
        Optional<ToDo> toDo1 = toDoRepository.findById(id);
        if (!toDo1.isPresent()) {
            throw new Exception(String.format("ToDo with id: %d does not exist!", id));
        }
        toDo1.get().setToDo(toDo.getToDo());
        toDo1.get().setIsFinished(toDo.getIsFinished());
        toDoRepository.save(toDo1.get());
        return toDo1;
    }

    @Override
    public List<ToDo> listOfToDos() {
        return toDoRepository.findAll();
    }

    @Override
    public ToDo getToDoById(Integer id) throws Exception {
        Optional<ToDo> toDo = toDoRepository.findById(id);
        if (!toDo.isPresent()) {
            throw new Exception(String.format("ToDo with id: %d does not exist!", id));
        }
        return toDo.get();
    }
}
