package com.thoughtworks.springbootemployee.service;

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

    public Employee getEmployeeByID(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployeesByPage(Integer page, Integer pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
        if(employees == null){
            return null;
        }
        return employees.getContent();
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    public Employee deleteEmployee(Integer id) {
        Employee employee = getEmployeeByID(id);
        employeeRepository.deleteById(id);
        return employee;
    }
}
