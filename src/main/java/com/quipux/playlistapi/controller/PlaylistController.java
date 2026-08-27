package com.quipux.playlistapi.controller;
import com.quipux.playlistapi.model.Musica; // Nova Feature - Inclusão da musica na Playlist
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

    // POST /lists - cria uma nova playlist

    @PostMapping
    public ResponseEntity<Playlist> criar(@RequestBody Playlist playlist) {
        Playlist criada = playlistService.criarPlaylist(playlist);


        URI location = UriComponentsBuilder.fromPath("/lists/{nome}")
                .buildAndExpand(criada.getNome())
                .toUri();
        return ResponseEntity.created(location).body(criada);
    }

    // GET /lists - lista todas as playlists

    @GetMapping
    public ResponseEntity<List<Playlist>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    // GET /lists/{listName} - busca uma playlist pelo nome

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


    // POST /lists/{listName}/musicas - inclui uma música numa playlist existente

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

}