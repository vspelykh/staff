package com.andersenlab.staff.service.specification;

import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import com.andersenlab.staff.model.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String HIRE_DATE = "hireDate";

    private GetEmployeesRequest request;

    @Override
    public Predicate toPredicate(@NonNull Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        predicate = applyFirstNameFilter(root, criteriaBuilder, predicate);
        predicate = applyLastNameFilter(root, criteriaBuilder, predicate);
        applySorting(root, query, criteriaBuilder);

        return predicate;
    }

    private Predicate applyFirstNameFilter(Root<Employee> root, CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (request.getFirstName() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(FIRST_NAME)), "%" + request.getFirstName().toLowerCase() + "%"));
        }
        return predicate;
    }

    private Predicate applyLastNameFilter(Root<Employee> root, CriteriaBuilder criteriaBuilder, Predicate predicate) {
        if (request.getLastName() != null) {
            return criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(LAST_NAME)), "%" + request.getLastName().toLowerCase() + "%"));
        }
        return predicate;
    }

    private void applySorting(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (request.getSortBy() != null) {
            String[] sortParts = request.getSortBy().split(",");
            if (sortParts.length == 2) {
                String field = sortParts[0];
                String direction = sortParts[1].equalsIgnoreCase("desc") ? "DESC" : "ASC";
                addOrder(root, query, criteriaBuilder, field, direction);
            }
        }
    }

    private void addOrder(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, String field, String direction) {
        if (FIRST_NAME.equalsIgnoreCase(field)) {
            query.orderBy("ASC".equalsIgnoreCase(direction) ? criteriaBuilder.asc(root.get(FIRST_NAME)) : criteriaBuilder.desc(root.get(FIRST_NAME)));
        } else if (LAST_NAME.equalsIgnoreCase(field)) {
            query.orderBy("ASC".equalsIgnoreCase(direction) ? criteriaBuilder.asc(root.get(LAST_NAME)) : criteriaBuilder.desc(root.get(LAST_NAME)));
        } else {
            query.orderBy("ASC".equalsIgnoreCase(direction) ? criteriaBuilder.asc(root.get(HIRE_DATE)) : criteriaBuilder.desc(root.get(HIRE_DATE)));
        }
    }
}
