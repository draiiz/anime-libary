package hu.anime.libary.AnimeLibary;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.service.AnimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    // Konstruktor injektálás: a legjobb gyakorlat
    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/getall")
    public List<Anime> getAllAnime(){
        return animeService.getAllAnime();
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id){
        Optional<Anime> anime = animeService.getAnimeById(id);
        if(!anime.isEmpty())
            return new ResponseEntity<Anime>(anime.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Anime> createAnime(@RequestBody Anime newAnime){
        System.out.println(newAnime.getTitle());
        Anime createAnime = animeService.addAnime(newAnime);
        return new ResponseEntity<>(createAnime, HttpStatus.CREATED);
    }

    @PutMapping("/updateanime/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable Long id,@RequestBody Anime anime){
        Anime updateAnime = animeService.updateAnime(id,anime);
        if(updateAnime == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
       return new ResponseEntity<>(updateAnime,HttpStatus.OK);
    }

    @DeleteMapping("/delete-anime")
    public ResponseEntity<Void> deleteAnime(@RequestBody Anime anime){
        animeService.deleteAnime(anime);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}
