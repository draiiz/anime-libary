package hu.anime.libary.AnimeLibary;

import hu.anime.libary.AnimeLibary.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnimeServiceSpringTest {


    @Autowired
    private AnimeService animeService;

    @Test
    public void countByStatus_withDataBaseConnection_returnRealCount(){
        //given
        String status = "COMPLETED";

        //when
        long result = animeService.countByStatus(status);

        //than
        assertEquals(0, result);
    }

}
