package com.avaand.app.controller;

import com.avaand.app.domain.Person;
import com.avaand.app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping("/api/person")
    public List<Person> getPersons(int pageNo, int pageSize, String sortBy){
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Person> page = personRepository.findAll(pageable);
        List<Person> content = page.getContent();
        return content;
    }

}
