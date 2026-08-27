package com.quipux.playlistapi.service;

import com.quipux.playlistapi.exception.NomeInvalidoException;
import com.quipux.playlistapi.exception.PlaylistNaoEncontradaException;
import com.quipux.playlistapi.model.Musica;
import com.quipux.playlistapi.model.Playlist;
import com.quipux.playlistapi.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    // ---------- criarPlaylist ----------

    @Test
    void criarPlaylist_comNomeValido_deveSalvarERetornarPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setNome("Lista de Rock");
        playlist.setDescricao("Minhas favoritas");

        when(playlistRepository.findByNome("Lista de Rock")).thenReturn(Optional.empty());
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        Playlist resultado = playlistService.criarPlaylist(playlist);

        assertNotNull(resultado);
        assertEquals("Lista de Rock", resultado.getNome());
        verify(playlistRepository).save(playlist);
    }

    @Test
    void criarPlaylist_comNomeNulo_deveLancarNomeInvalidoException() {
        Playlist playlist = new Playlist();
        playlist.setNome(null);

        assertThrows(NomeInvalidoException.class, () -> playlistService.criarPlaylist(playlist));
        verify(playlistRepository, never()).save(any());
    }

    @Test
    void criarPlaylist_comNomeVazio_deveLancarNomeInvalidoException() {
        Playlist playlist = new Playlist();
        playlist.setNome("   ");

        assertThrows(NomeInvalidoException.class, () -> playlistService.criarPlaylist(playlist));
        verify(playlistRepository, never()).save(any());
    }

    @Test
    void criarPlaylist_comNomeDuplicado_deveLancarNomeInvalidoException() {
        Playlist playlistExistente = new Playlist();
        playlistExistente.setNome("Lista de Rock");

        Playlist novaPlaylist = new Playlist();
        novaPlaylist.setNome("Lista de Rock");

        when(playlistRepository.findByNome("Lista de Rock")).thenReturn(Optional.of(playlistExistente));

        assertThrows(NomeInvalidoException.class, () -> playlistService.criarPlaylist(novaPlaylist));
        verify(playlistRepository, never()).save(any());
    }

    // ---------- listarTodas ----------

    @Test
    void listarTodas_deveRetornarListaDePlaylists() {
        Playlist playlist1 = new Playlist();
        playlist1.setNome("Lista 1");
        Playlist playlist2 = new Playlist();
        playlist2.setNome("Lista 2");

        when(playlistRepository.findAll()).thenReturn(List.of(playlist1, playlist2));

        List<Playlist> resultado = playlistService.listarTodas();

        assertEquals(2, resultado.size());
        verify(playlistRepository).findAll();
    }

    // ---------- buscarPorNome ----------

    @Test
    void buscarPorNome_comNomeExistente_deveRetornarPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setNome("Lista de Rock");

        when(playlistRepository.findByNome("Lista de Rock")).thenReturn(Optional.of(playlist));

        Playlist resultado = playlistService.buscarPorNome("Lista de Rock");

        assertNotNull(resultado);
        assertEquals("Lista de Rock", resultado.getNome());
    }

    @Test
    void buscarPorNome_comNomeInexistente_deveLancarPlaylistNaoEncontradaException() {
        when(playlistRepository.findByNome("NaoExiste")).thenReturn(Optional.empty());

        assertThrows(PlaylistNaoEncontradaException.class,
                () -> playlistService.buscarPorNome("NaoExiste"));
    }

    // ---------- deletarPorNome ----------

    @Test
    void deletarPorNome_comNomeExistente_deveChamarDelete() {
        Playlist playlist = new Playlist();
        playlist.setNome("Lista de Rock");

        when(playlistRepository.findByNome("Lista de Rock")).thenReturn(Optional.of(playlist));

        playlistService.deletarPorNome("Lista de Rock");

        verify(playlistRepository).delete(playlist);
    }

    @Test
    void deletarPorNome_comNomeInexistente_deveLancarPlaylistNaoEncontradaException() {
        when(playlistRepository.findByNome("NaoExiste")).thenReturn(Optional.empty());

        assertThrows(PlaylistNaoEncontradaException.class,
                () -> playlistService.deletarPorNome("NaoExiste"));
        verify(playlistRepository, never()).delete(any());
    }

    // ---------- adicionarMusica ----------

    @Test
    void adicionarMusica_comPlaylistExistente_deveSalvarMusicaNaPlaylist() {
        Playlist playlist = new Playlist();
        playlist.setNome("Lista de Rock");

        Musica musica = new Musica();
        musica.setTitulo("Bohemian Rhapsody");
        musica.setArtista("Queen");

        when(playlistRepository.findByNome("Lista de Rock")).thenReturn(Optional.of(playlist));
        when(playlistRepository.save(playlist)).thenReturn(playlist);

        Playlist resultado = playlistService.adicionarMusica("Lista de Rock", musica);

        assertEquals(1, resultado.getMusicas().size());
        assertEquals(playlist, musica.getPlaylist());
        verify(playlistRepository).save(playlist);
    }

    @Test
    void adicionarMusica_comTituloVazio_deveLancarNomeInvalidoException() {
        Musica musica = new Musica();
        musica.setTitulo("   ");

        assertThrows(NomeInvalidoException.class,
                () -> playlistService.adicionarMusica("Lista de Rock", musica));
        verify(playlistRepository, never()).save(any());
    }

    @Test
    void adicionarMusica_comPlaylistInexistente_deveLancarPlaylistNaoEncontradaException() {
        Musica musica = new Musica();
        musica.setTitulo("Bohemian Rhapsody");

        when(playlistRepository.findByNome("NaoExiste")).thenReturn(Optional.empty());

        assertThrows(PlaylistNaoEncontradaException.class,
                () -> playlistService.adicionarMusica("NaoExiste", musica));
        verify(playlistRepository, never()).save(any());
    }
}