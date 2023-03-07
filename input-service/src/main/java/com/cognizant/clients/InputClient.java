package com.cognizant.clients;

import com.cognizant.models.UserInput;
import com.cognizant.repositories.InputRepository;
import com.cognizant.services.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;



public class InputClient {
//
//    @Autowired
//   private WebClient webClient;
//   private InputService inputService;
//
////    private InputClient(WebClient webClient) {
////        this.webClient = webClient;
////    }
//
//    ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
//            .maxConnections(1000)
//            .pendingAcquireMaxCount(2000)
//            .build();
//    ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider));
//
//    @Autowired
//    public InputClient(WebClient.Builder builder, InputService inputService){
//        this.webClient = builder.clientConnector(clientHttpConnector).baseUrl("http://localhost:8080").defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//        this.inputService = inputService;
//    }
//
//
//    @RequestMapping(method= RequestMethod.GET, path="/tickets/get-ticket/{id}")
//    public Mono<UserInput> findById(String id) {
//
//        return webClient.get()
//                .uri("/tickets/get-ticket/"+id)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(UserInput.class)
//                .log();
//
//
//
//    }

}

