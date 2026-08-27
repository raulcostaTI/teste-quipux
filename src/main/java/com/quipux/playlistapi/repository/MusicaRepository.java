package com.quipux.playlistapi.repository;

import com.quipux.playlistapi.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
}