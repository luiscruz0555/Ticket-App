package com.cognizant.handlers;

import com.cognizant.models.UserInput;
import com.cognizant.repositories.InputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class InputHandler {

//    private InputRepository inputRepository;
//
//    @Autowired
//    public InputHandler(InputRepository inputRepository) {
//        this.inputRepository = inputRepository;
//    }
//
//    public Mono<ResponseEntity<ServerResponse>> findById(int id) {
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(inputRepository.findById(id)));
//    }

}
