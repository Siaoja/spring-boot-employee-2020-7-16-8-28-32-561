package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class CompanyServiceTest {
    @Test
    void should_return_companies_when_getAll_given_none() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        companies.add(new Company());
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        given(companyRepository.findAll()).willReturn(companies);

        //when
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> returnCompanies = companyService.getAllCompanies();

        //then
        assertEquals(2,returnCompanies.size());
    }


    @Test
    void should_return_companies_when_get_companies_then_given_pages() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        companies.add(new Company());
        companies.add(new Company());
        CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
        given(companyRepository.findAll(1,2)).willReturn(companies.subList(0,2));

        //when
        CompanyService companyService = new CompanyService(companyRepository);
        List<Company> returnCompanies = companyService.getAllCompaniesByPage(1,2);

        //then
        assertEquals(2,returnCompanies.size());

    }
}
