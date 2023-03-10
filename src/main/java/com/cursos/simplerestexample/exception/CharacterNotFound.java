package com.cursos.simplerestexample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Character not found")
public class CharacterNotFound extends RuntimeException {
}
