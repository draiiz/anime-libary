package hu.anime.libary.AnimeLibary.controller;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.service.AnimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AnimeController Tesztek")
public class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeService animeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Anime testAnime;

    @BeforeEach
    public void setUp() {
        testAnime = new Anime();
        testAnime.setId(1L);
        testAnime.setAnimeName("Death Note");
        testAnime.setStatus("COMPLETED");
        testAnime.setEpisodeNumber(37);
        testAnime.setPlotTwist("Great plot twist");
    }

    @Test
    @DisplayName("GET /api/anime/getall - Összes anime")
    public void testGetAllAnime() throws Exception {
        when(animeService.getAllAnime()).thenReturn(java.util.List.of(testAnime));

        mockMvc.perform(get("/api/anime/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].animeName").value("Death Note"));
    }

    @Test
    @DisplayName("GET /api/anime/getbyid/{id} - Anime lekérése")
    public void testGetAnimeById() throws Exception {
        when(animeService.getAnimeById(1L)).thenReturn(Optional.of(testAnime));

        mockMvc.perform(get("/api/anime/getbyid/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeName").value("Death Note"));
    }

    @Test
    @DisplayName("POST /api/anime/create - Új anime létrehozása")
    public void testCreateAnime() throws Exception {
        when(animeService.addAnime(any(Anime.class))).thenReturn(testAnime);

        mockMvc.perform(post("/api/anime/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAnime)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.animeName").value("Death Note"));
    }

    @Test
    @DisplayName("PUT /api/anime/updateanime/{id} - Anime frissítése")
    public void testUpdateAnime() throws Exception {
        when(animeService.updateAnime(1L, testAnime)).thenReturn(testAnime);

        mockMvc.perform(put("/api/anime/updateanime/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAnime)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeName").value("Death Note"));
    }

    @Test
    @DisplayName("DELETE /api/anime/{id} - Anime törlése")
    public void testDeleteAnime() throws Exception {
        doNothing().when(animeService).deleteAnime(1L);

        mockMvc.perform(delete("/api/anime/delete-anime/1"))
                .andExpect(status().isNoContent());
    }
}