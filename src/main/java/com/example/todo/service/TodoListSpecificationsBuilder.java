package com.example.todo.service;

import com.example.todo.dto.SearchCriteria;
import com.example.todo.enums.SearchOperation;
import com.example.todo.model.TodoList;
import com.example.todo.model.TodoList;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TodoListSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public TodoListSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public TodoListSpecificationsBuilder with(
            String key, String operation, Object value, String prefix, String suffix) {

        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SearchCriteria(key, op, value));
        }
        return this;
    }

    public Specification<TodoList> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new TodoListSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new TodoListSpecification(params.get(i)))
                    : Specification.where(result).and(new TodoListSpecification(params.get(i)));
        }

        return result;
    }
}
