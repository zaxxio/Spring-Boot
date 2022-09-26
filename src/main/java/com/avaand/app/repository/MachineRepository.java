package com.avaand.app.repository;

import com.avaand.app.machine.domain.Machine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends CrudRepository<Machine, Long> {

}
