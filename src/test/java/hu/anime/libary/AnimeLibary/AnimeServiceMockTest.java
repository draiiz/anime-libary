package hu.anime.libary.AnimeLibary;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.repository.AnimeRepository;
import hu.anime.libary.AnimeLibary.service.AnimeService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class AnimeServiceMockTest {

    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepository;

    @Test
    public void countByStatus_withMockedSolution_ReturnCorrectCount(){
        String status = "COMPLETED";
        List<Anime> animeList = new ArrayList<>();
        Anime anime = new Anime();
        anime.setId(1L);
        anime.setAnimeName("Alma");
        anime.setStatus("COMPLETED");
        anime.setEpisodeNumber(37);
        anime.setPlotTwist("");

        Anime anime1 = new Anime();
        anime1.setId(2L);
        anime1.setAnimeName("Barack");
        anime1.setStatus("COMPLETED");
        anime1.setEpisodeNumber(897);
        anime1.setPlotTwist("");

        animeList.add(anime);
        animeList.add(anime1);

        when(animeRepository.countByStatus(status)).thenReturn(2L);

        long result = animeService.countByStatus(status);

        assertEquals(2L, result);
    }
}
