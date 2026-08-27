package com.quipux.playlistapi.exception;

public class NomeInvalidoException extends RuntimeException {

    public NomeInvalidoException(String mensagem) {
        super(mensagem);
    }
}