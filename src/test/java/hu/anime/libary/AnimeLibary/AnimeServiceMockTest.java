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
    public void getStatus_withMockedSoulution_ReturnBurnedDatas(){
        String stringCase = "Done";
        List<Anime> animeList = new ArrayList<>();
        Anime anime = new Anime(1L,"Alma", "Done", "", 37);
        Anime anime1 = new Anime(2L, "Barack", "Done", "", 897);

        animeList.add(anime);
        animeList.add(anime1);

        when(animeRepository.findAll()).thenReturn(animeList);

        int result = animeService.getStatus(stringCase);

        assertEquals(2,result);

    }
}
