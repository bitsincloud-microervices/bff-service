package com.example.bff.controller;

import com.example.bff.dto.ClientDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bff/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientBffController {

    private final WebClient clienteWebClient;

    @PostMapping
    public Mono<ResponseEntity<String>> create(@RequestBody @Valid ClientDTO clientDTO) {
        // Log do corpo da requisição
        log.info("Recebendo cliente: {}", clientDTO);

        // Transformação: por exemplo, forçar nome em maiúsculas
        clientDTO.setName(clientDTO.getName().toUpperCase());

        return clienteWebClient.post()
                .uri("/clients")
                .bodyValue(clientDTO)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Resposta do microserviço: {}", response))
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<Object> list() {
        log.info("Listando clientes via BFF");

        return clienteWebClient.get()
                .uri("/clients")
                .header("X-App-Token", "chave-secreta")
                .retrieve()
                .bodyToFlux(Object.class);
    }
}
