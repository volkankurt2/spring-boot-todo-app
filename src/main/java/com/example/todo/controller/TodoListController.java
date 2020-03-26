package com.example.todo.controller;

import com.example.todo.dto.TodoListDTO;
import com.example.todo.service.TodoListService;
import com.example.todo.service.mapper.TodoListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todolists")
@Slf4j
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;
    private final TodoListMapper todoListMapper;

    @GetMapping
    public ResponseEntity<List<TodoListDTO>> findAllBySpecificationAndSort(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        if (search != null) {
            return ResponseEntity.ok(todoListService.searchAndSort(search, sortBy, order));
        } else {
            return ResponseEntity.ok(todoListService.findAll(sortBy, order));
        }
    }
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody TodoListDTO todoListDTO) {
        return ResponseEntity.ok(todoListService.save(todoListMapper.todoListDTOToTodoList(todoListDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoListDTO> findById(@PathVariable Long id) {
        TodoListDTO todoListDTO = todoListService.findById(id);
        return ResponseEntity.ok(todoListDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoListDTO> update(@PathVariable Long id, @Valid @RequestBody TodoListDTO todoListDTO) {
        if (todoListService.findById(id) != null) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(todoListService.save(todoListMapper.todoListDTOToTodoList(todoListDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (todoListService.findById(id) != null) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        todoListService.delete(id);
        return ResponseEntity.ok().build();
    }
}