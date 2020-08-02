package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) throws NoSuchDataException {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<Employee> getEmployeeByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeService.getAllEmployeesByPage(page, pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByConditions(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(EmployeeMapper.EmployeeRequestToEmployee(employeeRequest));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) throws IllegalOperationException, NoSuchDataException {
        return employeeService.updateEmployee(id, EmployeeMapper.EmployeeRequestToEmployee(employeeRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Employee deleteEmployee(@PathVariable Integer id) throws NoSuchDataException {
        return employeeService.deleteEmployee(id);
    }

}
