package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.service.mapper.EmployeeMapper;
import com.andersenlab.staff.service.specification.EmployeeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private GetEmployeesRequest request;
    private Employee employee;
    private Specification<Employee> spec;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        request = GetEmployeesRequest.builder()
                .page(0)
                .size(10)
                .build();

        employee = Employee.builder()
                .firstName("John")
                .middleName("S.")
                .lastName("Doe")
                .birthDate(new Date(123345678))
                .hireDate(new Date(12345657))
                .type(EmployeeType.WORKER)
                .build();

        spec = new EmployeeSpecification(request);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void givenEmployeesExist_whenGetAllEmployees_thenReturnEmployeeList() {
        Page<Employee> employeePage = new PageImpl<>(Collections.singletonList(employee));
        when(employeeRepository.findAll(spec, pageable)).thenReturn(employeePage);

        Page<EmployeeDto> result = employeeService.getAllEmployees(request);

        verify(employeeMapper, times(1)).toDto(employee);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
    }

    @Test
    void givenNoEmployeesExist_whenGetAllEmployees_thenReturnEmptyList() {
        when(employeeRepository.findAll(spec, pageable)).thenReturn(Page.empty());

        Page<EmployeeDto> result = employeeService.getAllEmployees(request);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void givenMultipleEmployeesExist_whenGetAllEmployees_thenReturnFullEmployeeList() {
        Employee employee2 = Employee.builder()
                .firstName("Jane")
                .middleName("A.")
                .lastName("Smith")
                .birthDate(new Date(223345678))
                .hireDate(new Date(22345657))
                .type(EmployeeType.MANAGER)
                .build();

        Page<Employee> employeePageFull = new PageImpl<>(Arrays.asList(employee, employee2));

        when(employeeRepository.findAll(spec, pageable)).thenReturn(employeePageFull);

        Page<EmployeeDto> result = employeeService.getAllEmployees(request);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getFirstName());
        assertEquals("Jane", result.getContent().get(1).getFirstName());
    }

    @Test
    void givenEmployeeExists_whenGetEmployeeById_thenReturnEmployeeDto() {
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void givenEmployeeDoesNotExist_whenGetEmployeeById_thenThrowResourceNotFoundException() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }
}
