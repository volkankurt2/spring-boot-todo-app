package com.example.todo.controller;

import com.example.todo.dto.TodoItemDTO;
import com.example.todo.enums.SearchOperation;
import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoItemRepository;
import com.example.todo.service.TodoItemService;
import com.example.todo.service.TodoItemSpecificationsBuilder;
import com.example.todo.service.mapper.TodoItemMapper;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/todoitems")
@Slf4j
@RequiredArgsConstructor
public class TodoItemController {
    private final TodoItemService todoItemService;

    @GetMapping
    public ResponseEntity<List<TodoItemDTO>> findAllBySpecificationAndSort(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        if (search != null) {
            return ResponseEntity.ok(todoItemService.searchAndSort(search, sortBy, order));
        } else {
            return ResponseEntity.ok(todoItemService.findAll(sortBy, order));
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody TodoItemDTO todoItemDTO) {
        return ResponseEntity.ok(todoItemService.save(todoItemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItemDTO> findById(@PathVariable Long id) {
        TodoItemDTO todoItemDTO = todoItemService.findById(id);
        return ResponseEntity.ok(todoItemDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItemDTO> update(@PathVariable Long id, @Valid @RequestBody TodoItemDTO todoItemDTO) {
        if (todoItemService.findById(id) != null) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(todoItemService.save(todoItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (todoItemService.findById(id) != null) {
            log.error("Id " + id + " is not existed");
            ResponseEntity.badRequest().build();
        }

        todoItemService.delete(id);
        return ResponseEntity.ok().build();
    }
}