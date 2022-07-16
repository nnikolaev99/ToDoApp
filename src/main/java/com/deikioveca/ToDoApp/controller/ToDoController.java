package com.deikioveca.ToDoApp.controller;

import com.deikioveca.ToDoApp.entity.ToDo;
import com.deikioveca.ToDoApp.service.ToDoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/createToDo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        toDoService.createToDo(toDo);
        return ResponseEntity.status(HttpStatus.OK).body(toDo);
    }

    @DeleteMapping("/deleteToDo/{id}")
    public ResponseEntity<String> deleteToDo(@PathVariable Integer id) throws Exception {
        toDoService.deleteToDoById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully!");
    }

    @PutMapping("/updateToDo/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Integer id, @RequestBody ToDo toDo) throws Exception {
        toDoService.updateToDoById(id, toDo);
        return ResponseEntity.status(HttpStatus.OK).body(toDo);
    }

    @GetMapping("/listOfToDos")
    public ResponseEntity<List<ToDo>> listOfToDos() {
        List<ToDo> toDos = toDoService.listOfToDos();
        return ResponseEntity.status(HttpStatus.OK).body(toDos);
    }

    @GetMapping("/toDo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable Integer id) throws Exception {
        ToDo toDo = toDoService.getToDoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(toDo);
    }
}
