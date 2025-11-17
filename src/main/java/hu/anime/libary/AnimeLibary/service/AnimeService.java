package hu.anime.libary.AnimeLibary.service;

import hu.anime.libary.AnimeLibary.entity.Anime;
import hu.anime.libary.AnimeLibary.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    @Autowired
    AnimeRepository animeRepository;

    public Anime addAnime(Anime anime){
        return animeRepository.save(anime);
    }

    public List<Anime> getAllAnime(){
        return animeRepository.findAll();
    }

    public List<Anime> getAllShortedByName(){return animeRepository.findAllOrderByTitle();}

    public Optional<Anime> getAnimeById(Long id){
        return animeRepository.findById(id);
    }

    public void deleteAnime(Anime anime){
        animeRepository.delete(anime);
    }

    public Anime updateAnime(Long id, Anime anime){

        for( Anime animeUpdate : getAllAnime()){
            if(animeUpdate.getId().equals(id)){
                animeUpdate.setTitle(anime.getTitle());
                animeUpdate.setPlotTwist(anime.getPlotTwist());
                animeUpdate.setStatus(anime.getStatus());

                return animeUpdate;
            }
        }
        return null;
        // Elrontottam, mert ezzel a módszerrel felülírom az Animét
//        for(int i=0; i<getAllAnime().size();i++){
//            if(getAllAnime().get(i).getId().equals(id)){
//                getAllAnime().set(i, anime);
//                return getAllAnime().get(i);
//            }
//        }
//        return anime;
    }

    public Integer getStatus(String animeStatus){
        Integer countStatus = 0;
        for( Anime getStatus : getAllAnime()){
            if(getStatus.getStatus().equals(animeStatus)){
                countStatus ++;
            }
        }
        return countStatus;
    }
}
