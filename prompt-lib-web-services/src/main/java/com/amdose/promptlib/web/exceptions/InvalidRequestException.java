package com.amdose.promptlib.web.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Alaa Jawhar
 */
@Data
@AllArgsConstructor
public class InvalidRequestException extends RuntimeException {
    private String code;
    private String message;
}
