package com.avaand.app.mapper;

import com.avaand.app.domain.Employee;
import com.avaand.app.domain.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {
    @Mapping(source = "username", target = "empFirstName")
    @Mapping(source = "username", target = "empLastName")
    Employee toEmployee(Person person);
}
