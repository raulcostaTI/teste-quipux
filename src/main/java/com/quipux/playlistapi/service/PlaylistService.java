package com.quipux.playlistapi.service;

import com.quipux.playlistapi.exception.NomeInvalidoException;
import com.quipux.playlistapi.exception.PlaylistNaoEncontradaException;
import com.quipux.playlistapi.model.Musica;
import com.quipux.playlistapi.model.Playlist;
import com.quipux.playlistapi.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist criarPlaylist(Playlist playlist) {
        if (playlist.getNome() == null || playlist.getNome().isBlank()) {
            throw new NomeInvalidoException("O nome da lista é obrigatório");
        }
        if (playlistRepository.findByNome(playlist.getNome()).isPresent()) {
            throw new NomeInvalidoException("Já existe uma playlist com esse nome");
        }

        if (playlist.getMusicas() != null) {
            playlist.getMusicas().forEach(musica -> musica.setPlaylist(playlist));
        }

        return playlistRepository.save(playlist);
    }

    public List<Playlist> listarTodas() {
        return playlistRepository.findAll();
    }

    public Playlist buscarPorNome(String nome) {
        return playlistRepository.findByNome(nome)
                .orElseThrow(() -> new PlaylistNaoEncontradaException(
                        "Playlist com nome '" + nome + "' não encontrada"));
    }

    public void deletarPorNome(String nome) {
        Playlist playlist = buscarPorNome(nome);
        playlistRepository.delete(playlist);
    }

    @Transactional
    public Playlist adicionarMusica(String listName, Musica musica) {
        if (musica.getTitulo() == null || musica.getTitulo().isBlank()) {
            throw new NomeInvalidoException("O título da música é obrigatório");
        }

        Playlist playlist = buscarPorNome(listName);
        musica.setPlaylist(playlist);
        playlist.getMusicas().add(musica);

        return playlistRepository.save(playlist);
    }

    @Transactional
    public Playlist removerMusica(String listName, Long musicaId) {
        Playlist playlist = buscarPorNome(listName);
        boolean removeu = playlist.getMusicas().removeIf(musica -> musica.getId().equals(musicaId));

        if (!removeu) {
            throw new PlaylistNaoEncontradaException("Música não encontrada nesta playlist");
        }

        return playlistRepository.save(playlist);
    }
}