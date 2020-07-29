package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {
    CompanyRepository companyRepository;

    public CompanyService(){
    }
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public List<Company> getAllCompaniesByPage(Integer page, Integer pageSize) {
        return companyRepository.findAll(page,pageSize);
    }


    public Company getCompanyById(Integer id) {
        return null;
    }
}
