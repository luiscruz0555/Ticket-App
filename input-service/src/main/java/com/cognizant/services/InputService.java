package com.cognizant.services;

import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;
import com.cognizant.models.UserInput;
import com.cognizant.repositories.InputRepository;
import com.cognizant.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Service
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    private CaffeineCacheManager cache;

    Logger logger = LogManager.getLogger(InputService.class);


    public Flux<UserInput> findAll() throws TicketNotFoundException {
        logger.info("InputService: findAll() started");
        if (inputRepository.findAll() != null) {
            Flux<UserInput> tickets = inputRepository.findAll();

            logger.info("InputService: findAll() response payload {}", Mapper.mapToJsonString(inputRepository.findAll()));
            logger.info("InputService: findAll() succeeded in service");
            return tickets.delayElements(Duration.ofSeconds(1));
        }
        logger.info("InputService: findAll() failed, threw TicketNotFoundException");
        throw new TicketNotFoundException("Could not fetch Tickets!");
    }


    @Cacheable(value = "inputs", key = "'id'")
    public Mono<UserInput> findById(String id) throws TicketNotFoundException {

//        Mono<UserInput> ticket = inputRepository.findById(id);
//
//        return ticket;
        logger.info("InputService: findById() started");
        Mono<UserInput> ticketData = inputRepository.findById(id).cache();
        if (ticketData.equals(Mono.empty())) {
            logger.info("InputService: findById() failed, threw TicketNotFoundException");
            throw new TicketNotFoundException("That ticket does not exist!");
        }

        logger.info("InputService: findById() response payload {}", Mapper.mapToJsonString(inputRepository.findById(id)));
        logger.info("InputService: findById() succeeded in Service");
        return inputRepository.findById(id).cache();


    }

    public Flux<UserInput> findByAuthorId(String id) throws TicketNotFoundException {

        logger.info("InputService: findByAuthorId() started");

        if (!inputRepository.findByAuthorId(id).equals(Flux.empty())) {
            Flux<UserInput> userTickets = inputRepository.findByAuthorId(id);
            logger.info("InputService: findByAuthorId() response payload {}", Mapper.mapToJsonString(inputRepository.findByAuthorId(id)));
            logger.info("InputService: findByAuthorId() succeeded in Service");
            return userTickets;

        }
        logger.info("InputService: findByAuthorId() failed, threw TicketNotFoundException");
        throw new TicketNotFoundException("User tickets not found by that user!");
    }

    public Mono<UserInput> saveTicket(UserInput userInput) throws TicketNotValidException {
        logger.info("InputService: saveTicket() started");
        logger.info("InputService saveTicket() request payload {}", Mapper.mapToJsonString(userInput));
        if (userInput.getFirstname() == null ||
                userInput.getLastname() == null ||
                userInput.getAddress() == null ||
                userInput.getIssue() == null ||
                userInput.getAuthorId() == null) {
            logger.info("InputService: saveTicket() failed, input field empty, threw TicketNotValidException");
            throw new TicketNotValidException("Please ensure information was input correctly!");
        } else {
            logger.info("InputService: saveTicket() response payload {}", Mapper.mapToJsonString(inputRepository.save(userInput)));
            logger.info("InputService: saveTicket() succeeded in Service");
            return inputRepository.save(userInput);

        }

    }


}

