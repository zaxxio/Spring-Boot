package com.avaand.app.repository;

import com.avaand.app.domain.Consumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
    Consumer findConsumerByConsumerNameAndPassword(String consumerName, String password);
}
