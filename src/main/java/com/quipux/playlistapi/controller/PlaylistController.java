package com.quipux.playlistapi.controller;
import com.quipux.playlistapi.model.Musica;
import com.quipux.playlistapi.model.Playlist;
import com.quipux.playlistapi.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Playlist> criar(@RequestBody Playlist playlist) {
        Playlist criada = playlistService.criarPlaylist(playlist);

        URI location = UriComponentsBuilder.fromPath("/lists/{nome}")
                .buildAndExpand(criada.getNome())
                .toUri();
        return ResponseEntity.created(location).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    @GetMapping("/{listName}")
    public ResponseEntity<Playlist> buscarPorNome(@PathVariable String listName) {
        return ResponseEntity.ok(playlistService.buscarPorNome(listName));
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletar(@PathVariable String listName) {
        playlistService.deletarPorNome(listName);
        return ResponseEntity.noContent().build();
    }

    // POST /lists/{listName}/musicas - inclui uma música numa playlist
    @PostMapping("/{listName}/musicas")
    public ResponseEntity<Playlist> adicionarMusica(
            @PathVariable String listName,
            @RequestBody Musica musica) {

        Playlist atualizada = playlistService.adicionarMusica(listName, musica);

        URI location = UriComponentsBuilder
                .fromPath("/lists/{nome}")
                .buildAndExpand(atualizada.getNome())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(atualizada);
    }

    // DELETE /lists/{listName}/musicas/{musicaId} - remove uma música de uma playlist
    @DeleteMapping("/{listName}/musicas/{musicaId}")
    public ResponseEntity<Playlist> removerMusica(
            @PathVariable String listName,
            @PathVariable Long musicaId) {

        Playlist atualizada = playlistService.removerMusica(listName, musicaId);
        return ResponseEntity.ok(atualizada);
    }
}