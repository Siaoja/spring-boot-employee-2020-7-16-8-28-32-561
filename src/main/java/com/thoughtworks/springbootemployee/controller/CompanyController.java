package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    List<Company> companies = createNewCompany();

    @GetMapping
    public List<Company> getAllCompany() {
        return companies;
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {

        Company findedCompany = null;

        for(Company company : companies){
            if(company.getId() == id){
                findedCompany = company;
            }
        }

        return findedCompany;
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployees(@PathVariable int id) {

        List<Employee> employees = null;

        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.get(i).getEmployees();
            }
        }
        return null;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize) {

        int beginIndex = (page - 1) * pageSize;
        int endIndex = page * pageSize - 1;
        List<Company> displayCompanies = new ArrayList<>();
        for (; beginIndex <= endIndex && beginIndex < companies.size(); beginIndex++) {
            displayCompanies.add(companies.get(beginIndex));
        }
        return displayCompanies;
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return new Company(company.getId(), company.getCompanyName(), company.getEmployeesNumber(), company.getEmployees());
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {

        Company company = (companies.stream().filter(existCompany -> existCompany.getId() == id).findFirst().get());

        company.setCompanyName(updatedCompany.getCompanyName());
        company.setEmployees(updatedCompany.getEmployees());
        company.setEmployeesNumber(updatedCompany.getEmployeesNumber());

        return company;
    }

    @DeleteMapping("/{id}")
    public Company deleteCompanyById(@PathVariable int id) {

        Company deletedCompany = null;

        for(Company company : companies){
            if(company.getId() == id){
                deletedCompany = company;
            }
        }

        companies.remove(deletedCompany);

        return deletedCompany;
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
