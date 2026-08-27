package com.quipux.playlistapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(exclude = "playlist")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String artista;
    private String album;
    private String ano;
    private String genero;

    // Musica - parte 2 do relacionamento de tabelas.
    // Este é o lado "dono" do relacionamento: guarda a chave estrangeira playlist_id.
    @ManyToOne
    @JsonBackReference
    private Playlist playlist;
}