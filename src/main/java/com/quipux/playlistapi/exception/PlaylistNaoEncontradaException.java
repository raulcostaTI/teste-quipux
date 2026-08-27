package com.quipux.playlistapi.exception;

public class PlaylistNaoEncontradaException extends RuntimeException {

    public PlaylistNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}