package br.com.bio.exceptions;

public class CardFinishedException extends RuntimeException {
    public CardFinishedException(String message) {
        super(message);
    }
}