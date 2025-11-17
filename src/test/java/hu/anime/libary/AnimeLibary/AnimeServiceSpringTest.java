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
    public void getStatus_withDataBaseConnection_returnRealStatus(){
        //given
        String stringCase = "Done";

        //when
        int result = animeService.getStatus(stringCase);

        //than
        assertEquals(3,result);
    }

}
