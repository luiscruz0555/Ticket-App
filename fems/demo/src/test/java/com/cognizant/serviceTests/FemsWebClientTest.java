package com.cognizant.serviceTests;

import com.cognizant.clients.InputWebClient;
import com.cognizant.dtos.UserInputDTO;
import com.cognizant.exceptions.TicketNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;


@ExtendWith(SpringExtension.class)
public class FemsWebClientTest {
    String TEST_ID = "3";

    @Test
    public void shouldGetInputByTicketId() throws TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);

        UserInputDTO userInput = new UserInputDTO(
                "3", "Luis", "Cruz", "340 Encino Dr.", "comp broken", "PENDING", "63bd99767e6c5b1ceb833c0e"
        );

        Mono<UserInputDTO> userMono = Mono.just(userInput);

        given(inputWebClient.findById(TEST_ID)).willReturn(Mono.just(userInput));

        doReturn(Mono.just(userInput)).when(inputWebClient).findById(TEST_ID);

        StepVerifier
                .create(userMono)
                .expectNext(new UserInputDTO(
                        "3", "Luis", "Cruz", "340 Encino Dr.", "comp broken", "PENDING", "63bd99767e6c5b1ceb833c0e"))
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldGetTicketByAuthorId() throws TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);

        UserInputDTO userInput = new UserInputDTO(
                "3", "Luis", "Cruz", "340 Encino Dr.", "comp broken", "PENDING", "63bd99767e6c5b1ceb833c0e"
        );

        Flux<UserInputDTO> userMono = Flux.just(userInput);

        given(inputWebClient.findByAuthorId(TEST_ID)).willReturn(Flux.just(userInput));

        doReturn(Flux.just(userInput)).when(inputWebClient).findByAuthorId(TEST_ID);

        StepVerifier
                .create(userMono)
                .expectNext(new UserInputDTO(
                        "3", "Luis", "Cruz", "340 Encino Dr.", "comp broken", "PENDING", "63bd99767e6c5b1ceb833c0e"))
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldGetAllTickets() throws TicketNotFoundException {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);

        given(inputWebClient.findAll()).willReturn(Flux.just(new UserInputDTO("", "Test2", "", "", "", "", ""), new UserInputDTO("", "Test1", "", "", "", "", "")));

        when(inputWebClient.findAll()).thenReturn(Flux.just(new UserInputDTO("", "Test2", "", "", "", "", ""), new UserInputDTO("", "Test1", "", "", "", "", "")));

        Flux<UserInputDTO> fluxTickets = inputWebClient.findAll();

        StepVerifier
                .create(fluxTickets)
                .expectNext(new UserInputDTO("", "Test2", "", "", "", "", ""))
                .expectNext(new UserInputDTO("", "Test1", "", "", "", "", ""))
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldPostTicket() {

        InputWebClient inputWebClient = Mockito.mock(InputWebClient.class);

        Mono<UserInputDTO> testMono = Mono.just(new UserInputDTO("", "Test2", "", "", "", "", ""));

        given(inputWebClient.saveTicket(new UserInputDTO(null, "Test2", "", "", "", "", ""))).willReturn(testMono);

        when(inputWebClient.saveTicket(new UserInputDTO(null, "Test2", "", "", "", "", ""))).thenReturn(new UserInputDTO("", "Test2", "", "", "", "", ""));
        Mono<UserInputDTO> inputMono = inputWebClient.saveTicket(new UserInputDTO(null, "Test2", "", "", "", "", ""));

        StepVerifier
                .create(inputMono)
                .expectNext(new UserInputDTO("", "Test2", "", "", "", "", ""))
                .expectComplete()
                .verify();
    }


}



