package com.cognizant.clients;

import com.cognizant.dtos.UserInputDTO;
import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;
import com.cognizant.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class InputWebClient {


    private WebClient webClient;

    @Autowired
    public InputWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    Logger logger = LogManager.getLogger(InputWebClient.class);

    public Mono<UserInputDTO> findById(String id) throws TicketNotFoundException {
        logger.info("InputWebClient: findById() started");
        try {
            Mono<UserInputDTO> resp = this.webClient
                    .get()
                    .uri("/tickets/get-ticket/" + id)
                    .retrieve()
                    .bodyToMono(UserInputDTO.class)
                    .onErrorResume(WebClientResponseException.class,
                            ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex));
            logger.info("InputWebClient: findById() response payload {}", Mapper.mapToJsonString(resp));
            logger.info("InputWebClient: findById() ended...");

            return resp;

        } catch(WebClientResponseException e){
            logger.info("InputWebClient: findById() failed, threw TicketNotFoundException");
            throw new TicketNotFoundException("That ticket does not exist!");
        }
    }

    public Flux<UserInputDTO> findByAuthorId(String id) throws TicketNotFoundException {

        logger.info("InputWebClient: findByAuthorId() started");

        Flux<UserInputDTO> resp = this.webClient
                   .get()
                   .uri("/tickets/user/tickets/" + id)
                   .retrieve()
                   .bodyToFlux(UserInputDTO.class)
                   .onErrorResume(WebClientResponseException.class,
                           ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))
                   .log();
        logger.info("InputWebClient: findByAuthorId() response payload {}", Mapper.mapToJsonString(resp));
        logger.info("InputWebClient: findByAuthorId() ended...");

        return resp;


    }

    public Mono<UserInputDTO> saveTicket(UserInputDTO userInput) throws TicketNotFoundException, TicketNotValidException {

        logger.info("InputWebClient: saveTicket() started");

        if (userInput.getFirstname() == null ||
                userInput.getLastname() == null ||
                userInput.getAddress() == null ||
                userInput.getIssue() == null ||
                userInput.getAuthorId() == null){
            logger.info("InputWebClient: saveTicket() threw TicketNotValidException");
            logger.info("InputWebClient: saveTicket() ended...");
            throw new TicketNotValidException("Please ensure information was input correctly!");
        }

        Mono<UserInputDTO> resp = this.webClient
                  .post()
                  .uri("/tickets/new-ticket")
                  .body(Mono.just(userInput), UserInputDTO.class)
                  .retrieve()
                  .bodyToMono(UserInputDTO.class)
                  .onErrorResume(WebClientResponseException.class,
                          ex -> ex.getRawStatusCode() == 400 ? Mono.empty() : Mono.error(ex));


        logger.info("InputWebClient: saveTicket() response payload {}", Mapper.mapToJsonString(resp));
        logger.info("InputWebClient: saveTicket() ended...");
        return resp;

//      try {
//          return this.webClient
//                  .post()
//                  .uri("/tickets/new-ticket")
//                  .body(Mono.just(userInput), UserInputDTO.class)
//                  .retrieve()
//                  .bodyToMono(UserInputDTO.class)
//                  .onErrorResume(WebClientResponseException.class,
//                          ex -> ex.getRawStatusCode() == 400 ? Mono.empty() : Mono.error(ex));
//
//
//      }catch(WebClientResponseException.BadRequest e){
//          throw new TicketNotFoundException("Please ensure info was input correctly!");
//      }


    }

    public Flux<UserInputDTO> findAll() {
        logger.info("InputWebClient: findAll() started");

        Flux<UserInputDTO> resp = this.webClient
                .get()
                .uri("/tickets/all")
                .retrieve()
                .bodyToFlux(UserInputDTO.class)
                .onErrorResume(WebClientResponseException.class,
                        ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex));

        logger.info("InputWebClient: findAll() response payload {}", Mapper.mapToJsonString(resp));
        logger.info("InputWebClient: findAll() ended...");

        return resp;
    }
}
