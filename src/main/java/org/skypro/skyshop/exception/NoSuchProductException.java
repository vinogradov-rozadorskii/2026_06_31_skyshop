package org.skypro.skyshop.exception;

public class NoSuchProductException extends IllegalArgumentException {

    public NoSuchProductException() {
        super("Товар не найден");
    }
}