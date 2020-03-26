package com.example.todo.service;

import com.example.todo.dto.TodoListDTO;
import com.example.todo.dto.TodoListDTO;
import com.example.todo.enums.SearchOperation;
import com.example.todo.model.TodoList;
import com.example.todo.model.TodoList;
import com.example.todo.repository.TodoListRepository;
import com.example.todo.service.mapper.TodoListMapper;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TodoListService {

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoListMapper todoListMapper;

    public List<TodoListDTO> findAll() {
        return todoListMapper.todoListsToTodoListDTOs(todoListRepository.findAll());
    }

    public List<TodoListDTO> findAll(String sortBy, String order) {
        Sort sort = Sort.by(sortBy).ascending();
        if (order.equals("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        return todoListMapper.todoListsToTodoListDTOs(todoListRepository.findAll(sort));
    }

    public List<TodoListDTO> searchAndSort(String search, String sortBy, String order) {
        TodoListSpecificationsBuilder builder = new TodoListSpecificationsBuilder();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }

        Specification<TodoList> spec = builder.build();
        Sort sort = Sort.by(sortBy).ascending();
        if (order.equals("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        return todoListMapper.todoListsToTodoListDTOs(todoListRepository.findAll(spec, sort));
    }
    
    public TodoListDTO findById(Long id) {
        Optional<TodoList> todoList = todoListRepository.findById(id);
        return todoList.map(list -> todoListMapper.todoListToTodoListDTO(list)).orElse(null);

    }

    public TodoListDTO save(TodoList todoList) {
        return todoListMapper.todoListToTodoListDTO(todoListRepository.save(todoList));
    }

    public void delete(Long id) {
        todoListRepository.deleteById(id);
    }
}
