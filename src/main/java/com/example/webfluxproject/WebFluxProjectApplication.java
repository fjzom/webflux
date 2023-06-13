package com.example.webfluxproject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootApplication
public class WebFluxProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        flatMapMethod();
    }

    private static void filterMethod() {
        Flux<String> names  = Flux.just("Maria", "Juan", "Peter",  "Santiago")
                .map(name-> name.toUpperCase())
                .filter(name -> {
                    return name.length() == 5 ;
                })
                .doOnNext(n-> {
                    if (n.isEmpty())   throw new RuntimeException();
                });
        names.subscribe(n-> log.info(n),
                e-> log.error("Empty found", e),
                ()-> log.info("Process completed"));
    }

    public void flatMapMethod(){
        List<Usuario> usarioList = new ArrayList<>();
        usarioList.add(new Usuario("Juan"));
        usarioList.add(new Usuario("Maria"));
        usarioList.add(new Usuario("Peter"));
        usarioList.add(new Usuario("Santiago"));

        Flux.fromIterable(usarioList)
                .map(usuario -> usuario.getName().toUpperCase())
                .flatMap(nombre-> {
                    if(nombre.length() == 5 ) {
                        return Mono.just(nombre + "AAA");

                    }else
                        return  Mono.empty();
                })
                .map(n-> n.toLowerCase())
                .subscribe((u)-> log.info(u));

    }
}
