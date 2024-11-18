package br.com.deadsystem.api.orders.exceptions;

public class NegocioException extends RuntimeException{

    public NegocioException(String message) {
        super(message);
    }

}
