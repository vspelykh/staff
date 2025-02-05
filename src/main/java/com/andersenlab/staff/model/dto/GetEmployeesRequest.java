package com.andersenlab.staff.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetEmployeesRequest {

    private static final String DEFAULT_SORT_VALUE = "lastName,asc";
    private static final int MIN_PAGE_NUMBER = 0;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 50;

    @Pattern(regexp = "^[a-zA-Z]+,(asc|desc)$", message = "Invalid sortBy format(e.g., lastName,asc).")
    private String sortBy = DEFAULT_SORT_VALUE;

    @Min(value = MIN_PAGE_NUMBER, message = "Page number must be greater than or equal to 0.")
    private int page;

    @Min(value = MIN_PAGE_SIZE, message = "Size must be greater than or equal to 1.")
    @Max(value = MAX_PAGE_SIZE, message = "Size must be less than or equal to 50.")
    private int size;

    private String firstName;

    private String lastName;
}