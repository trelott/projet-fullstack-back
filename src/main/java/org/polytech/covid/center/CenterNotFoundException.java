package org.polytech.covid.center;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such center")
public class CenterNotFoundException extends RuntimeException{
}
