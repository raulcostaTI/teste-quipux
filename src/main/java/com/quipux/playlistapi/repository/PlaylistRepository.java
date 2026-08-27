package com.quipux.playlistapi.repository;

import com.quipux.playlistapi.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByNome(String nome); //Solicitado que a pesquisa seja por nome inves de Id

}