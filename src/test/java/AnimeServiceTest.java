package hu.anime.libary.AnimeLibary.service;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.exception.AnimeException;
import hu.anime.libary.AnimeLibary.repository.AnimeRepository;
import hu.anime.libary.AnimeLibary.service.AnimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("AnimeService Tesztek")
public class AnimeServiceTest {

    @Mock
    AnimeRepository animeRepository;

    @InjectMocks
    AnimeService animeService;

    private Anime testAnime;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testAnime = new Anime();
        testAnime.setId(1L);
        testAnime.setAnimeName("Death Note");
        testAnime.setStatus("COMPLETED");
        testAnime.setEpisodeNumber(37);
        testAnime.setPlotTwist("Great plot twist");
    }

    @Test
    @DisplayName("Új anime hozzáadása - Sikeres")
    public void testAddAnimeSuccess() {
        // Arrange
        when(animeRepository.existsByAnimeName("Death Note")).thenReturn(false);
        when(animeRepository.save(testAnime)).thenReturn(testAnime);

        // Act
        Anime result = animeService.addAnime(testAnime);

        // Assert
        assertNotNull(result);
        assertEquals("Death Note", result.getAnimeName());
        assertEquals("COMPLETED", result.getStatus());
        verify(animeRepository, times(1)).save(testAnime);
    }

    @Test
    @DisplayName("Új anime hozzáadása - Már létezik")
    public void testAddAnimeDuplicate() {
        // Arrange
        when(animeRepository.existsByAnimeName("Death Note")).thenReturn(true);

        // Act & Assert
        assertThrows(AnimeException.class, () -> animeService.addAnime(testAnime));
        verify(animeRepository, never()).save(testAnime);
    }

    @Test
    @DisplayName("Anime lekérése ID alapján - Sikeres")
    public void testGetAnimeByIdSuccess() {
        // Arrange
        when(animeRepository.findById(1L)).thenReturn(Optional.of(testAnime));

        // Act
        Optional<Anime> result = animeService.getAnimeById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Death Note", result.get().getAnimeName());
    }

    @Test
    @DisplayName("Anime lekérése ID alapján - Nem talált")
    public void testGetAnimeByIdNotFound() {
        // Arrange
        when(animeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Anime> result = animeService.getAnimeById(999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Anime lekérése név alapján - Sikeres")
    public void testGetAnimeByNameSuccess() {
        // Arrange
        when(animeRepository.findByAnimeName("Death Note")).thenReturn(Optional.of(testAnime));

        // Act
        Optional<Anime> result = animeService.getAnimeByName("Death Note");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Death Note", result.get().getAnimeName());
    }

    @Test
    @DisplayName("Anime frissítése - Sikeres")
    public void testUpdateAnimeSuccess() {
        // Arrange
        Anime updateData = new Anime();
        updateData.setAnimeName("Death Note");
        updateData.setStatus("WATCHING");
        updateData.setEpisodeNumber(25);
        updateData.setPlotTwist("Updated plot");

        when(animeRepository.findById(1L)).thenReturn(Optional.of(testAnime));
        when(animeRepository.existsByAnimeName("Death Note")).thenReturn(false);
        when(animeRepository.save(testAnime)).thenReturn(testAnime);

        // Act
        Anime result = animeService.updateAnime(1L, updateData);

        // Assert
        assertNotNull(result);
        assertEquals("WATCHING", result.getStatus());
        verify(animeRepository, times(1)).save(testAnime);
    }

    @Test
    @DisplayName("Anime törlése - Sikeres")
    public void testDeleteAnimeSuccess() {
        // Arrange
        when(animeRepository.existsById(1L)).thenReturn(true);

        // Act
        animeService.deleteAnime(1L);

        // Assert
        verify(animeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Anime törlése - Nem talált")
    public void testDeleteAnimeNotFound() {
        // Arrange
        when(animeRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(AnimeException.class, () -> animeService.deleteAnime(999L));
    }

    @Test
    @DisplayName("Anime keresése - Üres kifejezés")
    public void testSearchAnimeEmptyTerm() {
        // Act & Assert
        assertThrows(AnimeException.class, () -> animeService.searchAnime(""));
    }

    @Test
    @DisplayName("Darabszám számlálása státusz alapján")
    public void testCountByStatus() {
        // Arrange
        when(animeRepository.countByStatus("COMPLETED")).thenReturn(5L);

        // Act
        long result = animeService.countByStatus("COMPLETED");

        // Assert
        assertEquals(5L, result);
    }
}