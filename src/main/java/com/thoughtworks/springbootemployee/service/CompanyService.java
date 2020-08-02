package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
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


    public Company getCompanyById(Integer id) throws NoSuchDataException {
        Company company = companyRepository.findById(id).orElse(null);
        if(company == null)
            throw new NoSuchDataException();
        return company;
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            return null;
        }
        return company.getEmployees();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Integer id, Company company) throws IllegalOperationException, NoSuchDataException {
        if (id != company.getId()) {
            throw new IllegalOperationException();
        }
        getCompanyById(id);
        return companyRepository.save(company);
    }

    public Company deleteCompany(Integer id) throws NoSuchDataException {
        Company deletedCompany = getCompanyById(id);
        companyRepository.deleteById(id);
        return deletedCompany;
    }
}
