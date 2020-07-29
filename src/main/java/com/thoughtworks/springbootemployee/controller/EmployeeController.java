package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeByID(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam() Integer page, @RequestParam() Integer pageSize) {
        return employeeService.getAllEmployeesByPage(page,pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByConditions(@RequestParam() String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee updateEmployee) {
        return employeeService.updateEmployee(id,updateEmployee);
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }

}
