package com.example.todo.service;

import com.example.todo.dto.TodoItemDTO;
import com.example.todo.enums.SearchOperation;
import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoItemRepository;
import com.example.todo.service.mapper.TodoItemMapper;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private TodoItemMapper todoItemMapper;

    public List<TodoItemDTO> findAll(String sortBy, String order) {
        Sort sort = Sort.by(sortBy).ascending();
        if (order.equals("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        return todoItemMapper.todoItemsToTodoItemDTOs(todoItemRepository.findAll(sort));
    }

    public List<TodoItemDTO> searchAndSort(String search, String sortBy, String order) {
        TodoItemSpecificationsBuilder builder = new TodoItemSpecificationsBuilder();
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

        Specification<TodoItem> spec = builder.build();
        Sort sort = Sort.by(sortBy).ascending();
        if (order.equals("desc")) {
            sort = Sort.by(sortBy).descending();
        }

        return todoItemMapper.todoItemsToTodoItemDTOs(todoItemRepository.findAll(spec, sort));
    }

    public TodoItemDTO findById(Long id) {
        Optional<TodoItem> todoItem = todoItemRepository.findById(id);
        return todoItem.map(item -> todoItemMapper.todoItemToTodoItemDTO(item)).orElse(null);

    }

    public TodoItemDTO save(TodoItemDTO todoItemDTO) {
        TodoItem todoItem = todoItemMapper.todoItemDTOToTodoItem(todoItemDTO);
        return todoItemMapper.todoItemToTodoItemDTO(todoItemRepository.save(todoItem));
    }

    public void delete(Long id) {
        todoItemRepository.deleteById(id);
    }
}
