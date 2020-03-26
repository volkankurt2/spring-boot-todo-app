package com.example.todo.dto;

import com.example.todo.enums.TodoItemStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class TodoItemDTO implements Serializable {
    private Long id;
    private Long todoListId;
    private String name;
    private String description;
    private String deadLine;

    @Enumerated(EnumType.ORDINAL)
    private TodoItemStatus status;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
