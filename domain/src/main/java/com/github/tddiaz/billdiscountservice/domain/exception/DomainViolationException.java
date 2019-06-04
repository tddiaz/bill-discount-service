package com.github.tddiaz.billdiscountservice.domain.exception;

public class DomainViolationException extends RuntimeException {

    private static final long serialVersionUID = -5802401091390422680L;

    public DomainViolationException(String message) {
        super(message);
    }
}
