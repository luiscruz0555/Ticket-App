package com.cognizant.controllers;


import com.cognizant.clients.InputWebClient;
import com.cognizant.dtos.UserInputDTO;
import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;
import com.cognizant.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path="/fems")
@CrossOrigin
public class InputClientController {

    private InputWebClient inputWebClient;


    @Autowired
    public InputClientController(InputWebClient inputWebClient) {
        this.inputWebClient = inputWebClient;
    }


    @GetMapping(path="/all")
    public Flux<UserInputDTO> findAll(){
        log.info("InputController: findAll() started...");

        Flux<UserInputDTO> resp = inputWebClient.findAll()
                .switchIfEmpty(Mono.error(new TicketNotFoundException("Tickets could not be fetched!")))
                .log();
        log.info("InputController: findAll() response from Service {}", Mapper.mapToJsonString(resp));
        return resp;
    }

    @GetMapping(path="/new-ticket/{id}")
    public Mono<ResponseEntity<UserInputDTO>> findById(@PathVariable("id") String id) throws TicketNotFoundException {
//       try{
//           return inputWebClient.findById(id)
//                   .map(ResponseEntity::ok)
//                   .defaultIfEmpty(ResponseEntity.notFound().build())
//                   .log();
//       }catch(Exception e){
//           System.out.println("Exception occured " + e);
//           return Mono.just(new ResponseEntity<UserInputDTO>(HttpStatus.NOT_FOUND));
//       }
        log.info("InputController: findById started...");

        Mono<ResponseEntity<UserInputDTO>> resp = inputWebClient.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new TicketNotFoundException("User ticket could not be found!")))
                .log();

        log.info("InputController: findById() response from Service {}", Mapper.mapToJsonString(resp));
        return resp;

    }

    @GetMapping(path="/user/tickets/{id}")
    public Flux<UserInputDTO> findByAuthorId(@PathVariable("id") String id) throws Exception{
//        try {
//            return inputWebClient.findByAuthorId(id)
//                    .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
//        }catch(Exception e){
//            System.out.println("Exception occurred "+e);
//            throw new UserNotFoundException("User not found!");
//        }
        log.info("InputController: findByAuthorId started...");

        Flux<UserInputDTO> resp = inputWebClient.findByAuthorId(id)
                    .switchIfEmpty(Mono.error(new TicketNotFoundException("User tickets with ID " +id+ " could not be found!")));
        log.info("InputController: findByAuthorId() response from Service {}", Mapper.mapStreamToJsonString(resp));
        return resp;
    }

    @PostMapping(path="saveTicket")
    public Mono<UserInputDTO> saveTicket(@RequestBody UserInputDTO userInput) throws TicketNotFoundException, TicketNotValidException {
        log.info("InputClientController: saveTicket() persist input request payload {}", Mapper.mapToJsonString(userInput));
        Mono<UserInputDTO> resp = inputWebClient.saveTicket(userInput)
                   .switchIfEmpty(Mono.error(new TicketNotValidException("Please ensure information was input correctly!")))
                   .log();
        log.info("InputController: saveTicket() response from Service {}", Mapper.mapToJsonString(resp));
        return resp;




    }
}
