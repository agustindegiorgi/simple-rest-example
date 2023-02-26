package com.cursos.simplerestexample.controller;

import com.cursos.simplerestexample.exception.CharacterNotFound;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/characters")
@RestController
public class CharacterController {

    private Faker faker = new Faker();
    private List<String> characters = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            characters.add(faker.dragonBall().character());
        }
    }

    //@RequestMapping(value = "/dragonBall", method = RequestMethod.GET)
    @GetMapping(value = "/dragonBall")
    public List<String> getCharacters() {
        return characters;
    }

    @GetMapping(value = "/dragonBall/{name}")
    // Usamos @PathVariable (path params) cuando necesitamos buscar un elemento por un identificador.
    public String getCharacterByName(@PathVariable("name") String name) {
        return characters.stream()
                .filter(c -> c.equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s not found", name)));
    }

    @GetMapping(value = "/dragonBall/search")
    // Cuando quiera hacer una búsqueda filtrando datos, uso @RequestParam (query param).
    public List<String> getCharacterByPrefix(@RequestParam("prefix") String prefix) {
        List<String> result = characters.stream()
                .filter(c -> c.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
        return Optional.of(result)
                .filter(r -> !r.isEmpty())
                .orElseThrow(CharacterNotFound::new);
    }
}