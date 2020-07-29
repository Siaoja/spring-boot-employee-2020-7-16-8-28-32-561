package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {
    @Test
    void should_return_employees_when_getAll_given_none() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "hello", 18, "male", 3000));
        employees.add(new Employee(2, "hellome", 18, "male", 5000));
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> returnEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(2, returnEmployees.size());
    }

    @Test
    void should_return_employee_when_getById_given_id() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, "hello", 18, "male", 3000);
        given(employeeRepository.findByID(1)).willReturn(employee);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee returnEmployee = employeeService.getEmployeeByID(1);

        //then
        assertEquals(employee,returnEmployee);

    }
}
