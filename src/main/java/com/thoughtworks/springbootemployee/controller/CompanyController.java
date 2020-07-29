package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @GetMapping
    public List<Company> getAllCompany() {
        List<Company> companies = createNewCompany();

        return companies;
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        List<Company> companies = createNewCompany();
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.get(i);
            }
        }
        return null;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployees(@PathVariable int id) {
        List<Company> companies = createNewCompany();
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.get(i).getEmployees();
            }
        }
        return null;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize) {
        List<Company> companies = createNewCompany();
        int beginIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;
        List<Company> displayCompanies = new ArrayList<>();
        for (; beginIndex <= endIndex && beginIndex < companies.size(); beginIndex++) {
            displayCompanies.add(companies.get(beginIndex));
        }
        System.out.println(displayCompanies);
        return displayCompanies;
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return new Company(company.getId(), company.getCompanyName(), company.getEmployeesNumber(), company.getEmployees());
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company company) {
        List<Company> companies = createNewCompany();
        for (int index = 0; index < companies.size(); index++) {
            if (companies.get(index).getId() == id) {
                companies.get(index).setCompanyName(company.getCompanyName());
                companies.get(index).setEmployees(company.getEmployees());
                companies.get(index).setEmployeesNumber(company.getEmployeesNumber());
                return companies.get(index);
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public Company deleteCompanyById(@PathVariable int id) {
        List<Company> companies = createNewCompany();
        for (int index = 0; index < companies.size(); index++) {
            if (companies.get(index).getId() == id) {
                return companies.remove(index);
            }
        }
        return null;
    }

    private List<Company> createNewCompany() {
        List<Company> companies = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "alibaba1", 20, "male", 6000));
        employees.add(new Employee(2, "tengxun2", 19, "female", 7000));
        employees.add(new Employee(3, "alibaba3", 19, "male", 8000));
        companies.add(new Company(1, "alibaba", 200, employees));
        companies.add(new Company(2, "tencent", 200, employees));
        return companies;
    }
}
