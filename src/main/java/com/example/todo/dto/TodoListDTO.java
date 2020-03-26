package com.example.todo.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class TodoListDTO implements Serializable {
    private Long id;
    private String name;
    private UserDTO userDTO;
    private List<TodoItemDTO> todoItemDTOList;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
