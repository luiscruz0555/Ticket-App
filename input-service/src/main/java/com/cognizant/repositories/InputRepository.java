package com.cognizant.repositories;

import com.cognizant.models.UserInput;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InputRepository extends ReactiveMongoRepository<UserInput, String> {

    Mono<UserInput> findById(String id);

    Mono<UserInput> save(UserInput userInput);

    Flux<UserInput> findByAuthorId(String id);


}

