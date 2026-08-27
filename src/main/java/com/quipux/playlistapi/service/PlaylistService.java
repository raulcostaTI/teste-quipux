package com.quipux.playlistapi.service;

import com.quipux.playlistapi.exception.NomeInvalidoException;
import com.quipux.playlistapi.exception.PlaylistNaoEncontradaException;
import com.quipux.playlistapi.model.Playlist;
import com.quipux.playlistapi.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    // POST/lists - cria uma nova playlist
    public Playlist criarPlaylist(Playlist playlist) {
        if (playlist.getNome() == null || playlist.getNome().isBlank()) {
            throw new NomeInvalidoException("O nome da lista é obrigatório");
        }
        return playlistRepository.save(playlist);
    }

    // GET/lists - retorna todas as playlists

    public List<Playlist> listarTodas() {
        return playlistRepository.findAll();
    }

    // GET/lists/{listName} - busca uma playlist pelo nome

    public Playlist buscarPorNome(String nome) {
        return playlistRepository.findByNome(nome)
                .orElseThrow(() -> new PlaylistNaoEncontradaException(
                        "Playlist com nome '" + nome + "' não encontrada"));
    }

    // DELETE/lists/{listName} - apaga uma playlist

    public void deletarPorNome(String nome) {
        Playlist playlist = buscarPorNome(nome);
        playlistRepository.delete(playlist);
    }
}



// testar no postman