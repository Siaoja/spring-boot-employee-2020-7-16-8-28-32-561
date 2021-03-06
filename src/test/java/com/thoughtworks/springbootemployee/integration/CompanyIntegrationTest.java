package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MockMvc mockMvc;

    private List<Employee> employees;
    private List<Company> companies;

    @BeforeEach
    void setUp() {
        companies = new ArrayList<>();
        companies.add(new Company(null, "ali1", 100, null));
        companies.add(new Company(null, "tencent", 100, null));
        companies.add(new Company(null, "oocl", 100, null));
        companies = companyRepository.saveAll(companies);
        employees = new ArrayList<>();
        employees.add(employeeRepository.save(new Employee(null, "tencent", 18, "male", 2000, companies.get(1).getId())));
        employees.add(employeeRepository.save(new Employee(null, "mahuateng", 50, "male", 100000, companies.get(1).getId())));


    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_companies_when_hit_get_companies_endpoint_given_nothing() throws Exception {
        //given

        //when
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
        //then
    }

    @Test
    void should_get_company_when_hit_get_company_by_id_endpoint_given_id() throws Exception {
        //given
        Company company = companies.get(0);
        //when
        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));

        //then
    }

    @Test
    void should_get_the_first_and_second_company_when_hit_get_company_by_page_and_pageSize_endpoint_given_page_1_and_pageSize_2() throws Exception {
        //given
        Company firstCompany = companies.get(0);
        Company secondCompany = companies.get(1);
        int page = 1;
        int pageSize = 2;

        //when

        String requestURL = String.format("/companies?page=%s&pageSize=%s", page, pageSize);
        mockMvc.perform(get(requestURL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstCompany.getId()))
                .andExpect(jsonPath("$[0].companyName").value(firstCompany.getCompanyName()))
                .andExpect(jsonPath("$[0].employeesNumber").value(firstCompany.getEmployeesNumber()))
                .andExpect(jsonPath("$[1].id").value(secondCompany.getId()))
                .andExpect(jsonPath("$[1].companyName").value(secondCompany.getCompanyName()))
                .andExpect(jsonPath("$[1].employeesNumber").value(secondCompany.getEmployeesNumber()));

        //then
    }

    @Test
    void should_get_employees_when_hit_get_employees_given_company_id_1() throws Exception {
        //given
        int id = companies.get(1).getId();

        //when
        String requestURL = String.format("/companies/%s/employees", id);
        mockMvc.perform(get(requestURL))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(employees.get(0).getId()))
                .andExpect(jsonPath("$[0].age").value(employees.get(0).getAge()))
                .andExpect(jsonPath("$[0].gender").value(employees.get(0).getGender()))
                .andExpect(jsonPath("$[0].salary").value(employees.get(0).getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employees.get(0).getCompanyId()))
                .andExpect(jsonPath("$[1].id").value(employees.get(1).getId()))
                .andExpect(jsonPath("$[1].age").value(employees.get(1).getAge()))
                .andExpect(jsonPath("$[1].gender").value(employees.get(1).getGender()))
                .andExpect(jsonPath("$[1].salary").value(employees.get(1).getSalary()))
                .andExpect(jsonPath("$[1].companyId").value(employees.get(1).getCompanyId()));
        //then
    }

    @Test
    void should_get_added_company_when_hit_post_company_given_company() throws Exception {
        //given

        String companyInfo = "{\n" +
                "    \"companyName\":\"oocl\",\n" +
                "    \"employeesNumber\":200\n" +
                "}";

        //when
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(companyInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("oocl"))
                .andExpect(jsonPath("$.employeesNumber").value(200));


        //then
    }

    @Test
    void should_get_updated_company_when_hit_put_company_endpoint_given_id_and_company_info() throws Exception {
        //given

        String companyInfo = "{\n" +
                "    \"id\":" + companies.get(1).getId() + ",\n" +
                "    \"companyName\":\"oocl\",\n" +
                "    \"employeesNumber\":100\n" +
                "}";
        //when
        mockMvc.perform(put(("/companies/" + companies.get(1).getId())).contentType(MediaType.APPLICATION_JSON).content(companyInfo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("oocl"))
                .andExpect(jsonPath("$.employeesNumber").value(100));

        //then
    }

    @Test
    void should_return_status_accepted_when_delete_a_employee_given_id() throws Exception {
        //given
        Company company = companies.get(1);
        //when
        mockMvc.perform(delete(("/companies/" + company.getId())))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.companyName").value(company.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(company.getEmployeesNumber()));
        //then

    }
}
