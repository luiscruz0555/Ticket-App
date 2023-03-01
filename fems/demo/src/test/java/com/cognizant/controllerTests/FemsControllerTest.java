package com.cognizant.controllerTests;

import com.cognizant.clients.InputWebClient;
import com.cognizant.controllers.InputClientController;
import com.cognizant.dtos.UserInputDTO;
import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(InputClientController.class)
public class FemsControllerTest {

    private final String TEST_ID = "3";

    private final String TEST_AUTHOR = "63bd99767e6c5b1ceb833c0e";

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private InputWebClient inputWebClient;

    private ClientHttpConnector connector() {
        return new
                ReactorClientHttpConnector(HttpClient.create(ConnectionProvider.newConnection()));
    }

    @Test
    public void shouldGetInput() throws TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);

        UserInputDTO userInput = new UserInputDTO(
                "3", "Luis","Cruz","340 Encino Dr.","comp broken","PENDING","63bd99767e6c5b1ceb833c0e"
        );

        Mono<UserInputDTO> userMono = Mono.just(userInput);

        given(inputWebClient.findById(TEST_ID)).willReturn(Mono.just(userInput));

        doReturn(userMono).when(inputWebClient).findById(TEST_ID);

        webClient
                .get().uri("/fems/new-ticket/"+TEST_ID)
                .exchange()
                .expectBody(UserInputDTO.class);
    }

    @Test
    public void shouldGetInputByAuthorId() throws TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);


        UserInputDTO userInput = new UserInputDTO(
                "3", "Luis","Cruz","340 Encino Dr.","comp broken","PENDING",TEST_AUTHOR
        );

        Flux<UserInputDTO> userMono = Flux.just(userInput);

        given(inputWebClient.findByAuthorId(TEST_AUTHOR)).willReturn(Flux.just(userInput));

        when(inputWebClient.findByAuthorId(TEST_AUTHOR)).thenReturn(userMono);

        webClient
                .get().uri("/user/tickets/"+TEST_AUTHOR)
                .exchange()
                .expectBody(UserInputDTO.class);

    }

    @Test
    public void shouldGetAllTickets() throws TicketNotFoundException {

        UserInputDTO userInput = new UserInputDTO(
                "3", "Luis","Cruz","340 Encino Dr.","comp broken","PENDING","63bd99767e6c5b1ceb833c0e"
        );

        Flux<UserInputDTO> userFlux = Flux.just(userInput);

        given(inputWebClient.findAll()).willReturn(Flux.just(userInput));

        when(inputWebClient.findAll()).thenReturn(userFlux);

        webClient
                .get().uri("/fems/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserInputDTO.class);
    }

    @Test
    public void shouldPostTicket() throws TicketNotValidException, TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);


        UserInputDTO userInput = new UserInputDTO(
                null, "Luis","Cruz","340 Encino Dr.","comp broken","PENDING","63bd99767e6c5b1ceb833c0e"
        );

        Mono<UserInputDTO> userFlux = Mono.just(userInput);

        given(inputWebClient.saveTicket(userInput)).willReturn(Mono.just(userInput));

        when(inputWebClient.saveTicket(userInput)).thenReturn(userFlux);

        webClient
                .post().uri("/fems/new-ticket")
                .exchange()
                .expectBodyList(UserInputDTO.class);
    }



}
