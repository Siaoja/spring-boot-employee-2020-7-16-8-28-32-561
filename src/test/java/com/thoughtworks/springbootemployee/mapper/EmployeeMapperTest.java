package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Test
    void should_return_employee_when_employee_request_to_employee_given_employeeRequest() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest(null, "xiaoming", 20, "male", 1000, 3);

        //when
        Employee employee = EmployeeMapper.EmployeeRequestToEmployee(employeeRequest);

        //then
        assertEquals(employeeRequest.getAge(), employee.getAge());
        assertEquals(employeeRequest.getCompanyId(), employee.getCompanyId());
        assertEquals(employeeRequest.getGender(), employee.getGender());
    }
}
