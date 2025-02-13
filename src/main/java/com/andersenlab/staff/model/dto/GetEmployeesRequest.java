package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request for retrieving a list of employees with filtering and pagination")
public class GetEmployeesRequest {

    private static final String DEFAULT_SORT_VALUE = "lastName,asc";
    private static final int MIN_PAGE_NUMBER = 0;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 50;

    @Schema(description = "Sorting criteria in the format of 'field,direction' (e.g., 'lastName,asc'). Default is 'lastName,asc'.",
            example = "lastName,asc")
    @Pattern(regexp = "^[a-zA-Z]+,(asc|desc)$", message = "Invalid sortBy format(e.g., lastName,asc).")
    private String sortBy = DEFAULT_SORT_VALUE;

    @Schema(description = "Page number for pagination. Must be greater than or equal to 0.", example = "0")
    @Min(value = MIN_PAGE_NUMBER, message = "Page number must be greater than or equal to 0.")
    private int page;

    @Schema(description = "Page size for pagination. Must be between 1 and 50.", example = "20")
    @Min(value = MIN_PAGE_SIZE, message = "Size must be greater than or equal to 1.")
    @Max(value = MAX_PAGE_SIZE, message = "Size must be less than or equal to 50.")
    private int size;

    @Schema(description = "Filter employees by first name (optional)", example = "John")
    private String firstName;

    @Schema(description = "Filter employees by last name (optional)", example = "Doe")
    private String lastName;

    @Schema(description = "Filter employees by active status (optional)", example = "true")
    private Boolean active;

    @Schema(description = "Filter employees by type (optional)", example = "FULL_TIME")
    private EmployeeType type;
}
