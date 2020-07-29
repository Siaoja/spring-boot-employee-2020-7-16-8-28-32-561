package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService() {
    }

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(Integer id) {
        return employeeRepository.findByID(id);
    }

    public List<Employee> getAllEmployeesByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAllByPage(page, pageSize);
    }
}
