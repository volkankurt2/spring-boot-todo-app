package com.example.todo.service.mapper;


import com.example.todo.dto.TodoItemDTO;
import com.example.todo.model.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link TodoItem} and its DTO called {@link com.example.todo.dto.TodoItemDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class TodoItemMapper {

    @Autowired
    private TodoListMapper todoListMapper;

    public List<TodoItemDTO> todoItemsToTodoItemDTOs(List<TodoItem> todoItems) {
        if (todoItems == null) {
            return null;
        } else {
            return todoItems.stream()
                    .filter(Objects::nonNull)
                    .map(this::todoItemToTodoItemDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<TodoItem> todoItemDTOsToTodoItems(List<TodoItemDTO> todoItemDTOS) {
        if (todoItemDTOS == null) {
            return null;
        } else {
            return todoItemDTOS.stream()
                    .filter(Objects::nonNull)
                    .map(this::todoItemDTOToTodoItem)
                    .collect(Collectors.toList());
        }
    }

    public TodoItemDTO todoItemToTodoItemDTO(TodoItem todoItem) {
        return TodoItemDTO.builder()
                .id(todoItem.getId())
                .name(todoItem.getName())
                .description(todoItem.getDescription())
                .deadLine(todoItem.getDeadLine())
                .status(todoItem.getStatus())
                .todoListId(todoItem.getTodoList().getId())
                .createdAt(todoItem.getCreatedAt())
                .updatedAt(todoItem.getUpdatedAt()).build();
    }

    public TodoItem todoItemDTOToTodoItem(TodoItemDTO todoItemDTO) {
        if (todoItemDTO == null) {
            return null;
        } else {
            TodoItem todoItem = new TodoItem();
            todoItem.setId(todoItemDTO.getId());
            todoItem.setName(todoItemDTO.getName());
            todoItem.setDescription(todoItemDTO.getDescription());
            todoItem.setDeadLine(todoItemDTO.getDeadLine());
            todoItem.setStatus(todoItemDTO.getStatus());
            todoItem.setTodoList(todoListMapper.todoListFromId(todoItemDTO.getTodoListId()));

            return todoItem;
        }
    }

    public TodoItem todoItemFromId(Long id) {
        if (id == null) {
            return null;
        }

        return TodoItem.builder().id(id).build();
    }
}
