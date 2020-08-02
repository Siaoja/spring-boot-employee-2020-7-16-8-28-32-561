package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(employeeRepository);
        employees = new ArrayList<>();
        employees.add(new Employee(1, "hello", 18, "male", 3000));
        employees.add(new Employee(2, "hellome", 18, "female", 5000));
        employees.add(new Employee(3, "hellome", 18, "male", 5000));
    }

    @Test
    void should_return_employees_when_getAll_given_none() {
        //given
        given(employeeRepository.findAll()).willReturn(employees);
        //when
        List<Employee> returnEmployees = employeeService.getAllEmployees();

        //then
        assertEquals(3, returnEmployees.size());
    }

    @Test
    void should_return_employee_when_getById_given_id() throws NoSuchDataException {
        //given
        Integer id = 1;
        Employee employee = employees.get(0);
        given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

        //when
        Employee returnEmployee = employeeService.getEmployeeByID(id);

        //then
        assertEquals(employee, returnEmployee);

    }

    @Test
    void should_return_employees_when_get_employees_by_page_given_page_pageSize() {
        //given
        int page = 1, pageSize = 2;
        given(employeeRepository.findAll(PageRequest.of(page - 1, pageSize))).willReturn(new PageImpl<>(employees.subList(0, 2)));

        //when
        Page<Employee> returnEmployees = employeeService.getAllEmployeesByPage(page, pageSize);

        //then
        assertNotNull(returnEmployees);
        assertEquals(2, returnEmployees.getContent().size());
        assertEquals(employees.get(0), returnEmployees.getContent().get(0));
        assertEquals(employees.get(1), returnEmployees.getContent().get(1));

    }

    @Test
    void should_return_employees_when_get_employees_given_gender() {
        //given
        String gender = "male";
        given(employeeRepository.findAllByGender(gender)).willReturn(employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList()));

        //when
        List<Employee> returnEmployees = employeeService.getEmployeesByGender(gender);

        //then
        assertEquals(2, returnEmployees.size());
        assertEquals(employees.get(0),returnEmployees.get(0));
        assertEquals(employees.get(2),returnEmployees.get(1));

    }

    @Test
    void should_return_employee_when_add_employee_given_employee() {
        //given
        Employee employee = new Employee(1, "hello", 18, "male", 3000);
        given(employeeRepository.save(employee)).willReturn(employee);

        //when
        Employee returnEmployee = employeeService.addEmployee(employee);

        //then
        assertEquals(employee,returnEmployee);

    }

    @Test
    void should_return_employee_when_update_employee_given_employee() throws IllegalOperationException, NoSuchDataException {
        //given
        Employee employee = new Employee(1, "hello", 18, "male", 3000);
        Employee updatedEmployee = new Employee(1, "update", 18, "male", 3000);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
        given(employeeRepository.save(updatedEmployee)).willReturn(updatedEmployee);

        //when
        Employee returnEmployee = employeeService.updateEmployee(updatedEmployee.getId(), updatedEmployee);

        //then
        assertEquals(updatedEmployee,returnEmployee);

    }

    @Test
    void should_return_employee_when_delete_employee_given_employee_id() throws NoSuchDataException {
        //given
        Employee employee = new Employee(1, "hello", 18, "male", 3000);
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        //when
        Employee returnEmployee = employeeService.deleteEmployee(1);

        //then
        assertEquals(employee,returnEmployee);

    }

    @Test
    void should_throw_no_such_data_exception_when_find_by_id_given_not_exist_id() {
        //given
        int notExistId = 4;
        given(employeeRepository.findById(notExistId)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> employeeService.getEmployeeByID(notExistId));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_throw_illegal_operation_exception_when_update_employee_given_illegal_id_4_and_employee_id_3() {
        //given
        int illegalId = 4;
        Employee employee = employees.get(2);

        //when
        Exception exception = assertThrows(IllegalOperationException.class, () -> employeeService.updateEmployee(illegalId, employee));

        //then
        assertEquals(IllegalOperationException.class, exception.getClass());
    }

    @Test
    void should_throw_no_such_data_exception_when_update_employee_given_not_exist_id() {
        //given
        int notExistId = 5;
        Employee employee = new Employee(5, "123", 1, "female", 1000);
        given(employeeRepository.save(employee)).willReturn(null);

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> employeeService.updateEmployee(notExistId, employee));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_throw_no_such_data_exception_when_delete_employee_given_not_exist_id(){
        //given
        int notExistId = 5;
        given(employeeRepository.findById(notExistId)).willReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> employeeService.deleteEmployee(notExistId));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

}
