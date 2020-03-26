package com.example.todo.model;

import com.example.todo.enums.TodoItemStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "todo_item")
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem extends AuditModel implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(updatable = false, nullable = false, name = "todo_list_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FKEY_PROFILE_SEQ_IN_MOBILE"))
    private TodoList todoList;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="deadLine")
    private String deadLine;

    @Enumerated(EnumType.ORDINAL)
    private TodoItemStatus status;
}
