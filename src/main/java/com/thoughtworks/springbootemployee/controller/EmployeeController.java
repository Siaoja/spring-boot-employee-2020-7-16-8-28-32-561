package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @GetMapping
    public List<Employee> getAllEmployees(){
        return createNewEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        List<Employee> employees = createNewEmployees();
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeeByPage(@RequestParam(defaultValue = "null") int page, @RequestParam(defaultValue = "null") int pageSize) {
        List<Employee> employees = createNewEmployees();
        int beginIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize;
        List<Employee> responseEmployees = new ArrayList<>();
        for (; beginIndex < endIndex && beginIndex < employees.size(); beginIndex++) {
            responseEmployees.add(employees.get(beginIndex));
        }
        return responseEmployees;
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByConditions(@RequestParam(defaultValue = "null") String gender) {
        List<Employee> employees = createNewEmployees();
        List<Employee> responseEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getGender().equals(gender)) {
                responseEmployees.add(employee);
            }
        }
        return responseEmployees;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee){
        return new Employee(employee.getId(),employee.getName(),employee.getAge(),employee.getGender(),employee.getSalary());
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee updateEmployee){
        List<Employee> employees = createNewEmployees();
        for(Employee employee : employees){
            if(employee.getId() == id){
                employee.setAge(updateEmployee.getAge());
                employee.setGender(updateEmployee.getGender());
                employee.setName(updateEmployee.getName());
                employee.setSalary(updateEmployee.getSalary());
                return employee;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Employee deleteEmployee(@PathVariable int id){
        List<Employee> employees = createNewEmployees();
        for(int index = 0, length = employees.size(); index < length; index++){
            if(employees.get(index).getId() == id){
                return employees.remove(index);
            }
        }
        return null;
    }

    private List<Employee> createNewEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000));
        employees.add(new Employee(2, "tengxun2", 19, "female", 7000));
        employees.add(new Employee(3, "alibaba3", 19, "male", 8000));
        employees.add(new Employee(4, "alibaba4", 20, "male", 9000));
        employees.add(new Employee(5, "tengxun5", 19, "female", 10000));
        employees.add(new Employee(6, "alibaba6", 19, "male", 11000));
        employees.add(new Employee(7, "alibaba7", 19, "male", 12000));
        return employees;
    }
}
