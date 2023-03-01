package com.cognizant.config;

//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.reactive.ClientHttpConnector;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import org.springframework.web.reactive.function.client.WebClient;
//import io.netty.channel.ChannelOption;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import io.netty.handler.timeout.WriteTimeoutHandler;
//import reactor.netty.http.client.HttpClient;
//import reactor.netty.resources.ConnectionProvider;
//
//@Configuration
//@EnableWebFlux
//public class WebFluxConfig implements WebFluxConfigurer {
//    Logger logger = LoggerFactory.getLogger(WebFluxConfig.class);
//
//    @Bean
//    public WebClient getWebClient() {
//        HttpClient httpClient = HttpClient.create()
//                .tcpConfiguration(client ->
//                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
//                                .doOnConnected(conn -> conn
//                                        .addHandlerLast(new ReadTimeoutHandler(10))
//                                        .addHandlerLast(new WriteTimeoutHandler(10))));
//
//        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));
//
//        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
//            .maxConnections(1000)
//            .pendingAcquireMaxCount(2000)
//            .build();
//    ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider)
//            .tcpConfiguration(client ->
//            client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
//                    .doOnConnected(conn -> conn
//                            .addHandlerLast(new ReadTimeoutHandler(10))
//                            .addHandlerLast(new WriteTimeoutHandler(10)))));
//
//
//        return WebClient.builder()
//                .baseUrl("http://localhost:8080")
//                .clientConnector(clientHttpConnector)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }
//}
