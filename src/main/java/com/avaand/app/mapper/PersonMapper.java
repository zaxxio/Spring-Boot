package com.avaand.app.mapper;

import com.avaand.app.domain.Person;
import com.avaand.app.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    PersonDto toPersonDto(Person person);
    Person toPerson(PersonDto personDto);
}
