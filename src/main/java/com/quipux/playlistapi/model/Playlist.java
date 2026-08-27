package com.quipux.playlistapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "musicas")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @JsonProperty("descrição")
    private String descricao;

    // Playlist - parte 1 do relacionamento de tabelas.
    // mappedBy indica quem controla a key é a Musica.
    @JsonProperty("músicas")
    @JsonManagedReference
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Musica> musicas = new ArrayList<>();
}