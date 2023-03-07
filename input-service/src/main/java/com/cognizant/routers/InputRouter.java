package com.cognizant.routers;

import com.cognizant.handlers.InputHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class InputRouter {

//    @Bean
//    public RouterFunction route(InputHandler inputHandler) {
//        return RouterFunctions.route(GET("/tickets/get-ticket").and(accept(MediaType.APPLICATION_JSON)),
//                (inputHandler::findById));
//    }

}

