package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService() {
    }


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(Integer id) throws NoSuchDataException {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null){
            throw new NoSuchDataException();
        }
        return employee;
    }

    public Page<Employee> getAllEmployeesByPage(Integer page, Integer pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page - 1, pageSize));
        return employees;
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) throws IllegalOperationException, NoSuchDataException {
        if(id != employee.getId()){
            throw new IllegalOperationException();
        }
        employee.setId(id);
        Employee updatedEmployee = employeeRepository.save(employee);
        if(updatedEmployee == null){
            throw new NoSuchDataException();
        }
        return updatedEmployee;
    }

    public Employee deleteEmployee(Integer id) throws NoSuchDataException {
        Employee employee = getEmployeeByID(id);
        employeeRepository.deleteById(id);
        return employee;
    }
}
