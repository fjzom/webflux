package com.example.webfluxproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
@SpringBootApplication
public class WebFluxProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<String> names  = Flux.just("Juan", "Pedro",  "Santiago")
                .map(name-> name.toUpperCase())
                .doOnNext(n-> {
                    if (n.isEmpty())   throw new RuntimeException();
                });
        names.subscribe(n-> log.info(n),
                e-> log.error("Empty found", e),
                ()-> log.info("Process completed"));
    }
}
