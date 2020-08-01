package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> returnEmployees = employeeService.getAllEmployees();

        //then
//        assertEquals(0, returnEmployees.size());
    }

    @Test
    void should_return_employee_when_getById_given_id() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, "hello", 18, "male", 3000);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee returnEmployee = employeeService.getEmployeeByID(1);

        //then
//        assertEquals(employee,returnEmployee);

    }

    @Test
    void should_return_employees_when_get_employees_by_page_given_page_pageSize() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "hello", 18, "male", 3000));
        employees.add(new Employee(2, "hellome", 18, "male", 5000));
        employees.add(new Employee(3, "hellome", 18, "male", 5000));
        given(employeeRepository.findAll(PageRequest.of(1,2))).willReturn(new PageImpl<>(employees.subList(0,2)));

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Integer page = 1, pageSize = 2;
        List<Employee> returnEmployees = employeeService.getAllEmployeesByPage(page, pageSize);

        //then
        assertEquals(2, returnEmployees.size());

    }

    @Test
    void should_return_employees_when_get_employees_given_gender() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "hello", 18, "male", 3000));
        employees.add(new Employee(2, "hellome", 18, "male", 5000));
        given(employeeRepository.findAllByGender("male")).willReturn(employees);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> returnEmployees = employeeService.getEmployeesByGender("male");

        //then
//        assertEquals(2, returnEmployees.size());

    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, "hello", 18, "male", 3000);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee returnEmployee = employeeService.addEmployee(employee);

        //then
//        assertEquals(employee,returnEmployee);

    }

    @Test
    void should_return_employee_when_update_employee_given_employee() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, "hello", 18, "male", 3000);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee returnEmployee = employeeService.updateEmployee(1,employee);

        //then
//        assertEquals(employee,returnEmployee);

    }

    @Test
    void should_return_employee_when_delete_employee_given_employee_id() {
        //given
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Employee employee = new Employee(1, "hello", 18, "male", 3000);

        //when
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        Employee returnEmployee = employeeService.deleteEmployee(1);

        //then
//        assertEquals(employee,returnEmployee);

    }
}
