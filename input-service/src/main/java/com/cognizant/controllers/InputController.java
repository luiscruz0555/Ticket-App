package com.cognizant.controllers;

import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;

import com.cognizant.models.UserInput;
import com.cognizant.services.InputService;
import com.cognizant.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@CrossOrigin
public class InputController {

    private final InputService inputService;

    @Autowired
    public InputController(InputService inputService) {
        this.inputService = inputService;
    }

    Logger logger = LogManager.getLogger(InputController.class);

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserInput> findAll() throws TicketNotFoundException {
        logger.info("InputController: findAll() started");
        Flux<UserInput> allInputs = inputService.findAll().log();
        return allInputs;

    }

    @GetMapping("/get-ticket/{id}")
    public Mono<UserInput> findById(@PathVariable("id") String id) throws TicketNotFoundException {

        logger.info("InputController: findById started...");

        Mono<UserInput> ticket = inputService.findById(id)
                .switchIfEmpty(Mono.error(new TicketNotFoundException("User ticket not found!")))
                .log();

        logger.info("InputController: findById() response from Service {}", Mapper.mapToJsonString(ticket));
        return ticket;
//

//        try {
//            System.out.println(inputService.findById(id));
//            return inputService.findById(id)
//                    .map(ResponseEntity::ok)
//                    .defaultIfEmpty(ResponseEntity.notFound().build())
//                    .log();
//
//        } catch (TicketNotFoundException e) {
//            System.out.println("Exception occured" + e);
//            return Mono.just(new ResponseEntity<UserInput>(HttpStatus.NOT_FOUND)).log();
//        }

//        -----------------------------------------------------------------

//        if(!inputService.findById(id).equals(Mono.just(Mono.empty()))){
//            Mono<ResponseEntity<UserInput>> ticket = inputService.findById(id)
//                    .map(ResponseEntity::ok)
//                    .defaultIfEmpty(ResponseEntity.notFound().build())
//                    .log();
//            return ticket;
//        }
//
//        throw new TicketNotFoundException("Ticket with user ID " +id+ " not found!");


    }

    @GetMapping(path="/user/tickets/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserInput> findByAuthorId(@PathVariable("id") String id) throws TicketNotFoundException{
        logger.info("InputController: findByAuthorId started...");
        Flux<UserInput> resp = inputService.findByAuthorId(id)
                .switchIfEmpty(Mono.error(new TicketNotFoundException("User tickets could not be fetched!")))
                .log();

        logger.info("InputController: findByAuthorId() response from Service {}", Mapper.mapToJsonString(resp));
        return resp;

    }


    @PostMapping(path = "/new-ticket", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserInput> saveTicket(@RequestBody UserInput userInput) throws TicketNotValidException{
        logger.info("InputController: saveTicket() persist input request payload {}", Mapper.mapToJsonString(userInput));
        Mono<UserInput> ticket = inputService.saveTicket(userInput).log();
        logger.info("InputController: saveTicket() response from Service {}", Mapper.mapToJsonString(ticket));
        return ticket;
//        try {
//            return inputService.saveTicket(userInput)
//                    .log();
//        } catch (TicketNotValidException err) {
//            System.out.println("Exception occured: " + err);
//            return Mono.just(new ResponseEntity<UserInput>(HttpStatus.BAD_REQUEST)).log();
//        }


    }


//    WORKING CODE BELOW -- USED WITHOUT SERVICE
//    @GetMapping("/get-ticket/{id}")
//    public Mono<ResponseEntity<UserInput>> findById(@PathVariable("id") String id) {
////          return null; //FIX ME
//
////        return inputClient.findById(id);
//        UserInput ticket = inputRepository.findById(id);
//        Mono<UserInput> ticketMono = Mono.just(ticket).log();
//        return ticketMono
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build()).log();
//    }

    //WORKING CODE BELOW -- USED WITHOUT SERVICE
//    @PostMapping(path="/new-ticket", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Mono<ResponseEntity<UserInput>> saveTicket(@RequestBody UserInput userInput) {
//        UserInput ticket = inputRepository.save(userInput);
//        Mono<UserInput> ticketMono = Mono.just(ticket).log();
//        return ticketMono
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build()).log();
//    }
}

