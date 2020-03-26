package com.example.todo.service;

import com.example.todo.dto.SearchCriteria;
import com.example.todo.enums.SearchOperation;
import com.example.todo.model.TodoItem;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TodoItemSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public TodoItemSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public TodoItemSpecificationsBuilder with(
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

    public Specification<TodoItem> build() {
        if (params.size() == 0) {
            return null;
        }

        Specification result = new TodoItemSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new TodoItemSpecification(params.get(i)))
                    : Specification.where(result).and(new TodoItemSpecification(params.get(i)));
        }

        return result;
    }
}
