package com.quipux.playlistapi.controller;

import com.quipux.playlistapi.model.Playlist;
import com.quipux.playlistapi.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    // POST /lists - cria uma nova playlist

    @PostMapping
    public ResponseEntity<Playlist> criar(@RequestBody Playlist playlist) {
        Playlist criada = playlistService.criarPlaylist(playlist);
        return ResponseEntity.created(URI.create("/lists/" + criada.getNome())).body(criada);
    }

    // GET /lists - lista todas as playlists

    @GetMapping
    public ResponseEntity<List<Playlist>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    // GET /lists/{listName} - busca uma playlist/nome

    @GetMapping("/{listName}")
    public ResponseEntity<Playlist> buscarPorNome(@PathVariable String listName) {
        return ResponseEntity.ok(playlistService.buscarPorNome(listName));
    }

    // DELETE /lists/{listName} - apaga uma playlist/nome

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletar(@PathVariable String listName) {
        playlistService.deletarPorNome(listName);
        return ResponseEntity.noContent().build();
    }
}

