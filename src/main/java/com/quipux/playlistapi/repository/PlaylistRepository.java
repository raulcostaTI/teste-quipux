package com.quipux.playlistapi.repository;

import com.quipux.playlistapi.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByNome(String nome);

    List<Playlist> findByNomeContainingIgnoreCase(String nome);

}