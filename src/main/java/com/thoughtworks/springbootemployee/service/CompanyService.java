package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public CompanyService() {
    }

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getAllCompaniesByPage(Integer page, Integer pageSize) {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page - 1, pageSize));
        if (companies == null) {
            return null;
        }
        return companies.getContent();
    }


    public Company getCompanyById(Integer id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null || company.getEmployees() == null) {
            return null;
        }
        return company.getEmployees();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Integer id, Company company) {
        company.setId(id);
        return companyRepository.save(company);
    }

    public Company deleteCompany(Integer id) {
        Company deletedCompany = companyRepository.findById(id).orElse(null);
        if (deletedCompany == null) {
            return null;
        }
        companyRepository.deleteById(id);
        return deletedCompany;
    }
}
