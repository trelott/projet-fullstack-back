package org.polytech.covid.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such center member")
public class UserNotFoundException extends RuntimeException{
}
