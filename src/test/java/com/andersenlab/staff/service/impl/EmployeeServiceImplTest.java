package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.*;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeDetails;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.repository.EmployeeDetailsRepository;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.service.ManagerService;
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

    @Mock
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Mock
    private ManagerService managerService;

    @Spy
    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private GetEmployeesRequest request;
    private CreateEmployeeRequest createRequest;
    private UpdateEmployeeRequest updateRequest;
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

        createRequest = CreateEmployeeRequest.builder()
                .firstName("John")
                .middleName("S.")
                .lastName("Doe")
                .birthDate(new Date(123345678))
                .hireDate(new Date(12345657))
                .type(EmployeeType.WORKER)
                .build();

        updateRequest = UpdateEmployeeRequest.builder()
                .firstName("UpdatedName")
                .middleName("UpdatedMiddle")
                .lastName("UpdatedLast")
                .birthDate(new Date(123345678))
                .hireDate(new Date(12345657))
                .description("Updated description")
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
        when(employeeRepository.findByIdAndActiveIsTrue(employeeId)).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void givenEmployeeDoesNotExist_whenGetEmployeeById_thenThrowResourceNotFoundException() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findByIdAndActiveIsTrue(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    void givenWorkerType_whenCreateEmployee_thenSaveEmployeeWithoutDetails() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.createEmployee(createRequest);

        assertNotNull(result);
        verify(employeeMapper, times(1)).toEntity(createRequest);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenOtherType_whenCreateEmployee_thenSetEmployeeDetails() {
        createRequest.setType(EmployeeType.OTHER);
        createRequest.setDescription("Director");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto result = employeeService.createEmployee(createRequest);

        assertNotNull(result);

        verify(employeeMapper, times(1)).toEntity(createRequest);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenValidUpdateRequest_whenUpdateEmployee_thenSaveUpdatedEmployee() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployee(employeeId, updateRequest);

        assertNotNull(result);
        assertEquals(updateRequest.getFirstName(), employee.getFirstName());
        assertEquals(updateRequest.getMiddleName(), employee.getMiddleName());
        assertEquals(updateRequest.getLastName(), employee.getLastName());
        assertEquals(updateRequest.getBirthDate(), employee.getBirthDate());
        assertEquals(updateRequest.getHireDate(), employee.getHireDate());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeMapper, times(1)).updateEntityFromRequest(updateRequest, employee);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenNonExistentEmployeeId_whenUpdateEmployee_thenThrowResourceNotFoundException() {
        UUID nonExistentId = UUID.randomUUID();
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(nonExistentId, updateRequest));

        verify(employeeRepository, times(1)).findById(nonExistentId);
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(employeeMapper, never()).toDto(any(Employee.class));
    }

    @Test
    void givenValidRequest_whenUpdateEmployeeType_thenUpdateSuccessfully() {
        UUID employeeId = UUID.randomUUID();
        UpdateEmployeeType updateTypeRequest = new UpdateEmployeeType(EmployeeType.MANAGER);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployeeType(employeeId, updateTypeRequest);

        assertNotNull(result);
        assertEquals(EmployeeType.MANAGER, employee.getType());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenWorkerTypeWithDetails_whenUpdateEmployeeType_thenRemoveDetails() {
        UUID employeeId = UUID.randomUUID();
        employee.setType(EmployeeType.OTHER);
        employee.setEmployeeDetails(new EmployeeDetails());

        UpdateEmployeeType updateTypeRequest = new UpdateEmployeeType(EmployeeType.WORKER);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployeeType(employeeId, updateTypeRequest);

        assertNotNull(result);
        assertEquals(EmployeeType.WORKER, employee.getType());
        assertNull(employee.getEmployeeDetails());

        verify(employeeDetailsRepository, times(1)).delete(any(EmployeeDetails.class));
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenManagerType_whenUpdateEmployeeType_thenRemoveAllSubordinates() {
        UUID employeeId = UUID.randomUUID();
        employee.setType(EmployeeType.MANAGER);

        UpdateEmployeeType updateTypeRequest = new UpdateEmployeeType(EmployeeType.OTHER);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDto result = employeeService.updateEmployeeType(employeeId, updateTypeRequest);

        assertNotNull(result);
        assertEquals(EmployeeType.OTHER, employee.getType());

        verify(managerService, times(1)).removeAllSubordinates(employeeId);
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).toDto(employee);
    }

    @Test
    void givenNonExistentEmployee_whenUpdateEmployeeType_thenThrowException() {
        UUID nonExistentId = UUID.randomUUID();
        UpdateEmployeeType updateTypeRequest = new UpdateEmployeeType(EmployeeType.WORKER);

        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployeeType(nonExistentId, updateTypeRequest));

        verify(employeeRepository, times(1)).findById(nonExistentId);
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(employeeMapper, never()).toDto(any(Employee.class));
    }

    @Test
    void givenEmployeeExists_whenDeactivateEmployee_thenEmployeeDeactivated() {
        UUID employeeId = UUID.randomUUID();
        employee.setId(employeeId);
        when(employeeRepository.findByIdAndActiveIsTrue(employeeId)).thenReturn(Optional.of(employee));

        employeeService.deactivateEmployee(employeeId);

        assertFalse(employee.isActive(), "Employee should be deactivated");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void givenEmployeeDoesNotExist_whenDeactivateEmployee_thenThrowResourceNotFoundException() {
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.findByIdAndActiveIsTrue(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deactivateEmployee(employeeId));

        verify(employeeRepository, never()).save(any());
    }
}
