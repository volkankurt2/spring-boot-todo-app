package com.example.todo.service.mapper;


import com.example.todo.dto.TodoListDTO;
import com.example.todo.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link TodoList} and its DTO called {@link TodoListDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class TodoListMapper {

    @Autowired
    private TodoItemMapper todoItemMapper;

    @Autowired
    private UserMapper userMapper;

    public List<TodoListDTO> todoListsToTodoListDTOs(List<TodoList> todoLists) {
        if (todoLists == null) {
            return null;
        } else {
            return todoLists.stream()
                    .filter(Objects::nonNull)
                    .map(this::todoListToTodoListDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<TodoList> todoListDTOsToTodoLists(List<TodoListDTO> todoListDTOs) {
        if (todoListDTOs == null) {
            return null;
        } else {
            return todoListDTOs.stream()
                    .filter(Objects::nonNull)
                    .map(this::todoListDTOToTodoList)
                    .collect(Collectors.toList());
        }
    }

    public TodoListDTO todoListToTodoListDTO(TodoList todoList) {
        return TodoListDTO.builder()
                .id(todoList.getId())
                .name(todoList.getName())
                .userDTO(userMapper.userToUserDTO(todoList.getUser()))
                .todoItemDTOList(todoItemMapper.todoItemsToTodoItemDTOs(todoList.getTodoItems()))
                .createdAt(todoList.getCreatedAt())
                .updatedAt(todoList.getUpdatedAt()).build();
    }

    public TodoList todoListDTOToTodoList(TodoListDTO todoListDTO) {
        if (todoListDTO == null) {
            return null;
        } else {
            TodoList todoList = new TodoList();
            todoList.setId(todoListDTO.getId());
            todoList.setName(todoListDTO.getName());
            todoList.setUser(userMapper.userDTOToUser(todoListDTO.getUserDTO()));
            todoList.setTodoItems(todoItemMapper.todoItemDTOsToTodoItems(todoListDTO.getTodoItemDTOList()));

            return todoList;
        }
    }

    public TodoList todoListFromId(Long id) {
        if (id == null) {
            return null;
        }
        
        return TodoList.builder().id(id).build();
    }
}
