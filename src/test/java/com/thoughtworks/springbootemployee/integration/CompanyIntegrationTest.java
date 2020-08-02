package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;
    List<Company> companies;

    @BeforeEach
    void setUp() {
        companies = new ArrayList<>();
        companies.add(new Company(null, "ali1", 100, null));
        companies.add(new Company(null, "tencent", 100, null));
        companies.add(new Company(null, "oocl", 100, null));
        companies = companyRepository.saveAll(companies);
    }

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
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
}
