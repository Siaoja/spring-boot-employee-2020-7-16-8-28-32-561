package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

public class CompanyServiceTest {
    CompanyService companyService;
    CompanyRepository companyRepository;
    List<Company> companies;

    @BeforeEach
    void setUp() {

        companyRepository = Mockito.mock(CompanyRepository.class);
        companyService = new CompanyService(companyRepository);
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "hello", 18, "male", 3000));
        employees.add(new Employee(2, "hellome", 18, "female", 5000));
        employees.add(new Employee(3, "hellome", 18, "male", 5000));
        companies = new ArrayList<>();
        companies.add(new Company(0, "alibaba", 200, employees));
        companies.add(new Company(1, "alibaba1", 200, employees));
        companies.add(new Company(2, "alibaba2", 200, employees));
    }

    @Test
    void should_return_companies_when_getAll_given_none() {
        //given
        given(companyRepository.findAll()).willReturn(companies);

        //when
        List<Company> returnCompanies = companyService.getAllCompanies();

        //then
        assertEquals(3, returnCompanies.size());
    }


    @Test
    void should_return_companies_when_get_companies_then_given_pages() {
        //given
        int page = 1, pageSize = 2;
        given(companyRepository.findAll(PageRequest.of(page - 1, pageSize))).willReturn(new PageImpl<>(companies.subList(0, 2)));

        //when
        List<Company> returnCompanies = companyService.getAllCompaniesByPage(page, pageSize);

        //then
        assertEquals(2, returnCompanies.size());

    }

    @Test
    void should_return_company_when_get_company_then_given_id() throws NoSuchDataException {
        //given
        int id = 1;
        given(companyRepository.findById(id)).willReturn(Optional.of(companies.get(id)));

        //when
        Company returnCompany = companyService.getCompanyById(1);

        //then
        assertEquals(companies.get(id), returnCompany);
    }

    @Test
    void should_return_employees_when_get_company_employees_then_given_id() {
        //given
        int id = 1;
        given(companyRepository.findById(id)).willReturn(Optional.of(companies.get(id)));

        //when
        List<Employee> returnEmployees = companyService.getEmployeesByCompanyId(1);

        //then
        assertEquals(companies.get(id).getEmployees(), returnEmployees);

    }

    @Test
    void should_return_company_when_add_company_then_given_company() {
        //given
        Company company = new Company(3, "alibaba", 2000, null);
        given(companyRepository.save(company)).willReturn(company);

        //when
        Company returnCompany = companyService.addCompany(company);

        //then
        assertEquals(company, returnCompany);

    }

    @Test
    void should_return_company_when_update_company_then_given_company() throws IllegalOperationException, NoSuchDataException {
        //given
        Company company = new Company(1, "ali", 1000, null);
        Company updatedCompany = new Company(1, "huawei", 2000, null);
        given(companyRepository.save(updatedCompany)).willReturn(updatedCompany);
        given(companyRepository.findById(1)).willReturn(Optional.of(company));

        //when
        Company returnCompany = companyService.updateCompany(updatedCompany.getId(), updatedCompany);

        //then
        assertEquals(updatedCompany, returnCompany);

    }

    @Test
    void should_return_company_when_delete_company_then_given_id() throws NoSuchDataException {
        //given
        int id = 1;
        given(companyRepository.findById(id)).willReturn(Optional.of(companies.get(id)));

        //when
        Company returnCompany = companyService.deleteCompany(id);

        //then
        assertEquals(companies.get(id), returnCompany);

    }

    @Test
    void should_throw_no_such_data_exception_when_get_company_by_id_given_not_exist_id() {
        //given
        int notExistId = 3;
        given(companyRepository.findById(notExistId)).willReturn((Optional.empty()));

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> companyService.getCompanyById(notExistId));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_throw_illegal_operation_exception_when_update_company_given_illegal_id_4_and_company_id_2() {
        //given
        int illegalId = 4;
        Company company = companies.get(2);

        //when
        Exception exception = assertThrows(IllegalOperationException.class, () -> companyService.updateCompany(illegalId, company));

        //then
        assertEquals(IllegalOperationException.class, exception.getClass());
    }

    @Test
    void should_throw_no_such_data_exception_when_update_company_given_not_exist_id() {
//        given
        int notExistId = 4;
        Company company = new Company(4, "huawei", 0, null);

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> companyService.updateCompany(notExistId, company));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }

    @Test
    void should_throw_no_such_data_exception_when_delete_company_given_not_exist_id() {
        //given
        int notExistId = 4;

        //when
        Exception exception = assertThrows(NoSuchDataException.class, () -> companyService.deleteCompany(notExistId));

        //then
        assertEquals(NoSuchDataException.class, exception.getClass());
    }
}
